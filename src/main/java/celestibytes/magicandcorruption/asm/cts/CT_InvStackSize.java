package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;
import celestibytes.magicandcorruption.asm.MagicAndCorruption_ASM;

public class CT_InvStackSize implements IClassTransformer {
	
	private static class TransfInv {
		public final String classSrgName;

		public final String methodName;
		public final String obf_methodName;
		
		public final int max;
		
		public TransfInv(String classSrgName, String methodName, String obf_methodName, int max) {
			this.classSrgName = classSrgName;
			this.methodName = methodName;
			this.obf_methodName = obf_methodName;
			this.max = max;
		}
	}
	
	private static final TransfInv[] transformedInventories = {
		new TransfInv("net.minecraft.entity.player.InventoryPlayer", "getInventoryStackLimit", "d", CT_Item.MAX_STACK_SIZE),
		new TransfInv("net.minecraft.tileentity.TileEntityChest", "getInventoryStackLimit", "d", CT_Item.MAX_STACK_SIZE)
	};

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes) {
		boolean obfuscated = MagicAndCorruption_ASM.isObfuscatedEnv();
		TransfInv inv = null;
		
		boolean change = false;
		for(TransfInv ti : transformedInventories) {
			if(srgName.equals(ti.classSrgName)) {
				change = true;
				inv = ti;
				break;
			}
		}
		
		if(!change) {
			return classBytes;
		}
		
		ClassReader cr = new ClassReader(classBytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		Iterator<MethodNode> iter = cn.methods.iterator();
		while(iter.hasNext()) {
			MethodNode mtd = iter.next();
			if(mtd.name.equals(obfuscated ? inv.obf_methodName : inv.methodName) && mtd.desc.equals("()I")) {
				Iterator<AbstractInsnNode> iter2 = mtd.instructions.iterator();
				while(iter2.hasNext()) {
					AbstractInsnNode insn = iter2.next();
					if(insn.getOpcode() == Opcodes.IRETURN) {
						AbstractInsnNode bipush = insn.getPrevious();
						if(bipush != null && bipush.getOpcode() == Opcodes.BIPUSH) {
							mtd.instructions.insertBefore(insn, new IntInsnNode(CT_Item.MAX_STACK_SIZE_OPCODE, inv.max));
							mtd.instructions.remove(bipush);
						} else {
							mtd.instructions.insertBefore(insn, new InsnNode(Opcodes.POP));
							mtd.instructions.insertBefore(insn, new IntInsnNode(CT_Item.MAX_STACK_SIZE_OPCODE, inv.max));
						}
						
						ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
						cn.accept(cw);
						
						System.out.println("[Magic and Corruption - CT: InvStackSize:" + srgName + "] success");
						return cw.toByteArray();
					}
				}
			}
		}
		
		System.out.println("[Magic and Corruption - CT: InvStackSize:" + srgName + "] fail");
		return classBytes;
	}


}
