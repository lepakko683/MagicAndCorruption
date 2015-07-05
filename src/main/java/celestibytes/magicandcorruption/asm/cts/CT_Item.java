package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_Item extends ClassTransformer {
	
	public static final int MAX_STACK_SIZE = 1000;
	public static final int MAX_STACK_SIZE_OPCODE = Opcodes.SIPUSH;

	public CT_Item() {
		super("net.minecraft.item.Item");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		ClassReader cr = new ClassReader(classBytes);
		
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		MethodNode mtd = findMethod("<init>", "()V", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.PUTFIELD) {
					FieldInsnNode fin = (FieldInsnNode) insn;
					if(fin.owner.equals(obfuscated ? "adb" : "net/minecraft/item/Item") && fin.name.equals(obfuscated ? "h" : "maxStackSize") && fin.desc.equals("I")) {
						InsnList inj = new InsnList();
						AbstractInsnNode bipush = fin.getPrevious();
						if(bipush != null && bipush.getOpcode() == Opcodes.BIPUSH) {
							mtd.instructions.remove(bipush);
						} else {
							inj.add(new InsnNode(Opcodes.POP));
						}
						
						inj.add(new IntInsnNode(Opcodes.SIPUSH, MAX_STACK_SIZE));
						
						mtd.instructions.insertBefore(fin, inj);
						
						ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
						cn.accept(cw);
						
						System.out.println("[Magic and Corruption - CT: Item] success");
						return cw.toByteArray();
					}
				}
			}
		}
		
		System.out.println("[Magic and Corruption - CT: Item] failed");
		return classBytes;
	}

}
