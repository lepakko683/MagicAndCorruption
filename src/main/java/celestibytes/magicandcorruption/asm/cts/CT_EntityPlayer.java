package celestibytes.magicandcorruption.asm.cts;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_EntityPlayer extends ClassTransformer {

	public CT_EntityPlayer() {
		super("net.minecraft.entity.player.EntityPlayer");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		
		MethodNode mtd = new MethodNode(Opcodes.ACC_PUBLIC, obfuscated ? "b" : "setSneaking", "(Z)V", null, null);
		InsnList ij = new InsnList();
		LabelNode lb = new LabelNode();
		LabelNode lb2 = new LabelNode();
		ij.add(new VarInsnNode(Opcodes.ALOAD, 0));
		ij.add(new VarInsnNode(Opcodes.ILOAD, 1));
		ij.add(new JumpInsnNode(Opcodes.IFEQ, lb)); // celestibytes/magicandcorruption/ASMCalls debugOutput (I)V false
		ij.add(new LdcInsnNode(.5f));
		ij.add(new InsnNode(Opcodes.ICONST_3));
		ij.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "debugOutput", "(I)V", false));
		ij.add(new JumpInsnNode(Opcodes.GOTO, lb2));
		ij.add(lb);
		ij.add(new InsnNode(Opcodes.FCONST_1));
		ij.add(new InsnNode(Opcodes.ICONST_2));
		ij.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "debugOutput", "(I)V", false));
		ij.add(lb2);
		ij.add(new FieldInsnNode(Opcodes.PUTFIELD, obfuscated ? "sa" : "net/minecraft/entity/Entity", obfuscated ? "W" : "stepHeight", "F"));
		ij.add(new VarInsnNode(Opcodes.ALOAD, 0));
		ij.add(new VarInsnNode(Opcodes.ILOAD, 1));
		ij.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, obfuscated ? "sa" : "net/minecraft/entity/Entity", obfuscated ? "b" : "setSneaking", "(Z)V", false));
		ij.add(new InsnNode(Opcodes.RETURN));
		
		mtd.instructions.add(ij);
		cn.methods.add(mtd);
		
		byte[] ret = getNewBytes(cn);
		System.out.println("[Magic and Corruption - CT: EntityPlayer] success");
//		try {
//			File out = new File("/home/okkapel/Programming/Minecraft/MagicAndCorruption/entityplayer.class");
//			DataOutputStream dos = new DataOutputStream(new FileOutputStream(out));
//			dos.write(ret);
//			dos.close();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		return ret;
	}

}
