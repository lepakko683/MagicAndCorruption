package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;
import celestibytes.magicandcorruption.asm.MagicAndCorruption_ASM;
import net.minecraft.launchwrapper.IClassTransformer;

public class CT_DebugOutput implements IClassTransformer {

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes) {
		if(MagicAndCorruption_ASM.isObfuscatedEnv()) {
			return classBytes;
		}
		
		if(srgName.equals("net.minecraft.network.NetHandlerPlayServer")) {
			ClassNode cn = ClassTransformer.getClassNode(classBytes);
			MethodNode mtd = ClassTransformer.findMethod("processClickWindow", "(Lnet/minecraft/network/play/client/C0EPacketClickWindow;)V", cn);
			
			if(mtd != null) {
				Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
				while(iter.hasNext()) {
					AbstractInsnNode insn = iter.next();
					if(insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
						MethodInsnNode min = (MethodInsnNode) insn;
						if(min.desc.equals("(Lnet/minecraft/inventory/Container;Ljava/util/List;)V") &&
								min.owner.equals("net/minecraft/entity/player/EntityPlayerMP") &&
								min.name.equals("sendContainerAndContentsToPlayer")) {
							mtd.instructions.insert(min, new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "debugOutput", "(I)V", false));
							mtd.instructions.insert(min, new IntInsnNode(Opcodes.BIPUSH, 1));
							
							System.out.println("[Magic and Corruption - CT: DebugOutput: NetHandlerPlayServer] success");
							return ClassTransformer.getNewBytes(cn);
						}
					}
				}
			}
			System.out.println("[Magic and Corruption - CT: DebugOutput: NetHandlerPlayServer] fail");
		}
		
		return classBytes;
	}

}
