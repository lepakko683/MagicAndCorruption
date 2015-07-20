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
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_NetHandlerPlayServer extends ClassTransformer {

	public CT_NetHandlerPlayServer() {
		super("net.minecraft.network.NetHandlerPlayServer");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		ClassReader cr = new ClassReader(classBytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		MethodNode mtd = findMethod(obfuscated ? "a" : "processCreativeInventoryAction", obfuscated ? "(Ljm;)V" : "(Lnet/minecraft/network/play/client/C10PacketCreativeInventoryAction;)V", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.IF_ICMPGT) {
					AbstractInsnNode bipush = insn.getPrevious();
					if(bipush != null && bipush.getOpcode() == Opcodes.BIPUSH) {
						AbstractInsnNode getfield = bipush.getPrevious();
						if(getfield != null && getfield.getOpcode() == Opcodes.GETFIELD) {
							FieldInsnNode fin = (FieldInsnNode) getfield;
							if(fin.desc.equals("I") && fin.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && fin.name.equals(obfuscated ? "b" : "stackSize")) {
								mtd.instructions.insertBefore(bipush, new VarInsnNode(Opcodes.ALOAD, 3));
								mtd.instructions.insertBefore(bipush, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "add" : "net/minecraft/item/ItemStack", obfuscated ? "e" : "getMaxStackSize", "()I", false));
								mtd.instructions.remove(bipush);
								
								break;
							}
						}
					}
				}
			}
		}
		
		mtd = findMethod(obfuscated ? "a" : "processEntityAction", obfuscated ? "(Ljj;)V" : "(Lnet/minecraft/network/play/client/C0BPacketEntityAction;)V", cn);
		if(mtd != null) {
			int found = 0;
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("(Z)V") && min.name.equals(obfuscated ? "b" : "setSneaking") && min.owner.equals(obfuscated ? "mw" : "net/minecraft/entity/player/EntityPlayerMP")) {
						mtd.instructions.insert(min, new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/network/NetworkHelper", "syncStepHeight", obfuscated ? "(Lmw;)V" : "(Lnet/minecraft/entity/player/EntityPlayerMP;)V", false));
						mtd.instructions.insert(min, new FieldInsnNode(Opcodes.GETFIELD, obfuscated ? "nh" : "net/minecraft/network/NetHandlerPlayServer", obfuscated ? "b" : "playerEntity", obfuscated ? "Lmw;" : "Lnet/minecraft/entity/player/EntityPlayerMP;"));
						mtd.instructions.insert(min, new VarInsnNode(Opcodes.ALOAD, 0));
						
						found++;
						if(found == 2) {
							System.out.println("[Magic and Corruption - CT: NetHandlerPlayServer] success");
							return getNewBytes(cn);
						}
					}
				}
			}
		}
		
 		System.out.println("[Magic and Corruption - CT: NetHandlerPlayServer] fail");
		return classBytes;
	}

}
