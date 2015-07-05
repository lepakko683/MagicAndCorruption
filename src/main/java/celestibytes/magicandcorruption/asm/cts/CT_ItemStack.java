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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_ItemStack extends ClassTransformer {

	public CT_ItemStack() {
		super("net.minecraft.item.ItemStack");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		int classModified = 0;
		
		ClassReader cr = new ClassReader(classBytes);
		
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		MethodNode mtd;
		mtd = findMethod(obfuscated ? "b" : "writeToNBT", obfuscated ? "(Ldh;)Ldh;" : "(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("(Ljava/lang/String;B)V") && min.name.equals(obfuscated ? "a" : "setByte")) {
						AbstractInsnNode cst = min.getPrevious();
						InsnList inj = new InsnList();
						
						if(cst != null && cst.getOpcode() == Opcodes.I2B) {
							mtd.instructions.remove(cst);
						} else {
							
							inj.add(new InsnNode(Opcodes.POP));
							inj.add(new FieldInsnNode(Opcodes.GETFIELD, obfuscated ? "add" : "net/minecraft/item/ItemStack", obfuscated ? "b" : "stackSize", "I"));
						}
						
						inj.add(new InsnNode(Opcodes.I2S));
						inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "dh" : "net/minecraft/nbt/NBTTagCompound", obfuscated ? "a" : "setShort", "(Ljava/lang/String;S)V", false));
						mtd.instructions.insertBefore(min, inj);
						mtd.instructions.remove(min);
						
						classModified |= 1;
						break;
					}
				}
			}
		}
		
		mtd = findMethod(obfuscated ? "c" : "readFromNBT", obfuscated ? "(Ldh;)V" : "(Lnet/minecraft/nbt/NBTTagCompound;)V", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("(Ljava/lang/String;)B") && min.name.equals(obfuscated ? "d" : "getByte")) {
						mtd.instructions.insertBefore(min, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "dh" : "net/minecraft/nbt/NBTTagCompound", obfuscated ? "e" : "getShort", "(Ljava/lang/String;)S", false));
						mtd.instructions.remove(min);
						
						classModified |= 2;
						break;
					}
				}
			}
		}
		
		if(classModified == 3) {
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cn.accept(cw);
			
			System.out.println("[Magic and Corruption - CT: ItemStack] success");
			return cw.toByteArray();
		} else {
			if(classModified == 1) {
				System.out.println("[Magic and Corruption - CT: ItemStack] failed, writeToNBT changed but not readFromNBT, this is bad!");				
			} else if(classModified == 2) {
				System.out.println("[Magic and Corruption - CT: ItemStack] failed, readFromNBT changed but not writeToNBT, this is bad!");
			} else {
				System.out.println("[Magic and Corruption - CT: ItemStack] failed, no changes done, this is bad!");
			}
		}
		
		return classBytes;
	}


}
