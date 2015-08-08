package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_Potion extends ClassTransformer {

	public static final int DEFAULT_POTION_ARRAY_SIZE = 127; // bytes -.-
	
	public CT_Potion() {
		super("net.minecraft.potion.Potion");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		for(FieldNode fn : cn.fields) {
			if(fn.name.equals(obfuscated ? "a" : "potionTypes") && fn.desc.equals(obfuscated ? "rv" : "net/minecraft/potion/Potion")) {
				fn.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC;
				break;
			}
		}
		
		MethodNode mn = findMethod("<clinit>", "()V", cn);
		if(mn != null) {
			Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.BIPUSH) {
					mn.instructions.insert(insn, new IntInsnNode(Opcodes.SIPUSH, DEFAULT_POTION_ARRAY_SIZE));
					mn.instructions.remove(insn);
					System.out.println("[Magic and Corruption - CT: Potion] success");
					return getNewBytes(cn);
				}
			}
		}
		
		
		System.out.println("[Magic and Corruption - CT: Potion] fail");
		
		return returnFail(classBytes);
	}

}
