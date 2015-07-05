package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_PacketBuffer extends ClassTransformer {

	public CT_PacketBuffer() {
		super("net.minecraft.network.PacketBuffer");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		ClassReader cr = new ClassReader(classBytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		int changes = 0;
		
		MethodNode mtd = null;
		mtd = findMethod(obfuscated ? "a" : "writeItemStackToBuffer", obfuscated ? "(Ladd;)V" : "(Lnet/minecraft/item/ItemStack;)V", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.GETFIELD) {
					FieldInsnNode fin = (FieldInsnNode) insn;
					if(fin.desc.equals("I") && fin.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && fin.name.equals(obfuscated ? "b" : "stackSize")) {
						AbstractInsnNode invk = fin.getNext();
						if(invk != null && invk.getOpcode() == Opcodes.INVOKEVIRTUAL) {
							MethodInsnNode min = (MethodInsnNode) invk;
							if(min.owner.equals(obfuscated ? "et" : "net/minecraft/network/PacketBuffer") && min.name.equals("writeByte") && min.desc.equals("(I)Lio/netty/buffer/ByteBuf;")) {
								mtd.instructions.insertBefore(min, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "et" : "net/minecraft/network/PacketBuffer" , "writeShort", "(I)Lio/netty/buffer/ByteBuf;", false));
								mtd.instructions.remove(min);
								
								changes |= 1;
							}
						}
					}
				}
			}
		}
		
		mtd = findMethod(obfuscated ? "c" : "readItemStackFromBuffer", obfuscated ? "()Ladd;" : "()Lnet/minecraft/item/ItemStack;", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("()B") && min.owner.equals(obfuscated ? "et" : "net/minecraft/network/PacketBuffer") && min.name.equals("readByte")) {
						mtd.instructions.insertBefore(min, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "et" : "net/minecraft/network/PacketBuffer", "readShort", "()S", false));
						mtd.instructions.remove(min);
						
						changes |= 2;
					}
				}
			}
		}
		
		if(changes == 3) {
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cn.accept(cw);
			
			System.out.println("[Magic and Corruption - CT: PacketBuffer] success");
			return cw.toByteArray();
		} else if(changes == 1) {
			System.out.println("[Magic and Corruption - CT: PacketBuffer] fail: only writeItemStackToBuffer was changed");
		} else if(changes == 2) {
			System.out.println("[Magic and Corruption - CT: PacketBuffer] fail: only readItemStackFromBuffer was changed");
		}
		
		return classBytes;
	}

}
