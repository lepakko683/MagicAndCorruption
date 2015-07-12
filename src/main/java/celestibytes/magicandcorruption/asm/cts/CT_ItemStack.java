package celestibytes.magicandcorruption.asm.cts;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import net.minecraft.item.ItemStack;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_ItemStack extends ClassTransformer {

	public CT_ItemStack() {
		super("net.minecraft.item.ItemStack");
		String[] dd = new String[4];
		int a = dd.length;
		String b = dd[2];
		dd[1] = "woot";
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		int classModified = 0;
		MethodNode mtd;
		
		ClassReader cr = new ClassReader(classBytes);
		
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		mtd = findMethod(obfuscated ? "a" : "useItemRightClick", obfuscated ? "(Lahb;Lyz;)Ladd;" : "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;", cn);
		if(mtd != null) { // run when no block was hovered over! we still want to be able to place blocks against furnaces and chests by sneaking
			InsnList inj = new InsnList();
			inj.add(new VarInsnNode(Opcodes.ALOAD, 2));
			inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "yz" : "net/minecraft/entity/player/EntityPlayer", obfuscated ? "an" : "isSneaking", "()Z", false));
			LabelNode skipCycle = new LabelNode();
			inj.add(new JumpInsnNode(Opcodes.IFEQ, skipCycle));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 0));
			inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "add" : "net/minecraft/item/ItemStack", obfuscated ? "b" : "getItem", obfuscated ? "()Ladb;" : "()Lnet/minecraft/item/Item;", false));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 0));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 1));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 2));
			inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "adb" : "net/minecraft/item/Item", "getNextItemInCycle", obfuscated ? "(Ladd;Lahb;Lyz;)[Ladd;" : "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)[Lnet/minecraft/item/ItemStack;", false));
			inj.add(new InsnNode(Opcodes.DUP));
			inj.add(new VarInsnNode(Opcodes.ASTORE, 3));
			inj.add(new JumpInsnNode(Opcodes.IFNULL, skipCycle));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 3));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 1));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 2));
			inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "handleCycleExtraItems", obfuscated ? "([Ladd;Lahb;Lyz;)V" : "([Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)V", false));
			inj.add(new VarInsnNode(Opcodes.ALOAD, 3));
			inj.add(new InsnNode(Opcodes.ICONST_0));
			inj.add(new InsnNode(Opcodes.AALOAD));
			inj.add(new InsnNode(Opcodes.ARETURN));
			inj.add(skipCycle);
			mtd.instructions.insert(inj);
		}
		
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
			
			byte[] ret = cw.toByteArray();
//			try {
//				File out = new File("/home/okkapel/Programming/Minecraft/MagicAndCorruption/itemstack.class");
//				DataOutputStream dos = new DataOutputStream(new FileOutputStream(out));
//				dos.write(ret);
//				dos.close();
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
			
			System.out.println("[Magic and Corruption - CT: ItemStack] success");
			return ret;
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
