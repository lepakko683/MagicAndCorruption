package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

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
		super("net.minecraft.item.ItemStack", "ItemStack");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		int classModified = 0;
		MethodNode mtd;
		
		ClassNode cn = getClassNode(classBytes);
		
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
			return getNewBytesLog(cn);
		} else {
			if(classModified == 1) {
				printStatus("failed, writeToNBT changed but not readFromNBT");
			} else if(classModified == 2) {
				printStatus("failed, readFromNBT changed but not writeToNBT");
			} else {
				printStatus("failed, no changes done");
			}
		}
		
		return returnFail(classBytes);
	}


}
