package celestibytes.magicandcorruption.asm.cts;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_Container extends ClassTransformer {

	public CT_Container() {
		super("net.minecraft.inventory.Container");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		MethodNode mtd = findMethod(obfuscated ? "a" : "mergeItemStack", obfuscated ? "(Ladd;IIZ)Z" : "(Lnet/minecraft/item/ItemStack;IIZ)Z", cn);
		
		// slot objref = 8
		
		if(mtd != null) {
			boolean flagStacksEqual = false; // have we found a call to ItemStack.areItemStackTagsEqual yet
			boolean flagFirstInj = false;
			boolean flagSecondInj = false;
			boolean flagThirdInj = false;
			boolean flagFourthInj = false;
			
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				
				if(!flagStacksEqual && insn.getOpcode() == Opcodes.INVOKESTATIC) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals(obfuscated ? "(Ladd;Ladd;)Z" : "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z") && min.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && min.name.equals(obfuscated ? "a" : "areItemStackTagsEqual")) {
						flagStacksEqual = true;
					}
				}
				
				// line 637 ~ forge 1291
				if(flagThirdInj && !flagFourthInj && insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("()I") && min.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && min.name.equals(obfuscated ? "e" : "getMaxStackSize")) {
						InsnList inj = new InsnList();
						inj.add(new VarInsnNode(Opcodes.ALOAD, 7));
						inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "aay" : "net/minecraft/inventory/Slot", obfuscated ? "a" : "getSlotStackLimit", "()I", false));
						inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "min", "(II)I", false));
						mtd.instructions.insert(min, inj);
						flagFourthInj = true;
					}
				}
				
				// line 636 ~ forge 1291
				if(flagSecondInj && !flagThirdInj && insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("()I") && min.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && min.name.equals(obfuscated ? "e" : "getMaxStackSize")) {
						InsnList inj = new InsnList();
						inj.add(new VarInsnNode(Opcodes.ALOAD, 7));
						inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "aay" : "net/minecraft/inventory/Slot", obfuscated ? "a" : "getSlotStackLimit", "()I", false));
						inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "min", "(II)I", false));
						mtd.instructions.insert(min, inj);
						flagThirdInj = true;
					}
				}
				
				// line 634 ~ forge 1291
				if(flagFirstInj && !flagSecondInj && insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("()I") && min.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && min.name.equals(obfuscated ? "e" : "getMaxStackSize")) {
						InsnList inj = new InsnList();
						inj.add(new VarInsnNode(Opcodes.ALOAD, 7));
						inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "aay" : "net/minecraft/inventory/Slot", obfuscated ? "a" : "getSlotStackLimit", "()I", false));
						inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "min", "(II)I", false));
						mtd.instructions.insert(min, inj);
						flagSecondInj = true;
					}
				}
				
				// line 627 ~ forge 1291
				if(flagStacksEqual && !flagFirstInj && insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("()I") && min.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && min.name.equals(obfuscated ? "e" : "getMaxStackSize")) {
						InsnList inj = new InsnList();
						inj.add(new VarInsnNode(Opcodes.ALOAD, 7));
						inj.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, obfuscated ? "aay" : "net/minecraft/inventory/Slot", obfuscated ? "a" : "getSlotStackLimit", "()I", false));
						inj.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "min", "(II)I", false));
						mtd.instructions.insert(min, inj);
						flagFirstInj = true;
					}
				}
				
				if(flagFourthInj && insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals(obfuscated ? "()Ladd;" : "()Lnet/minecraft/item/ItemStack;") && min.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && min.name.equals(obfuscated ? "m" : "copy")) {
						AbstractInsnNode invVirt = min.getNext();
						if(invVirt != null && invVirt.getOpcode() == Opcodes.INVOKEVIRTUAL) {
							MethodInsnNode min2 = (MethodInsnNode) invVirt;
							if(min2.desc.equals(obfuscated ? "(Ladd;)V" : "(Lnet/minecraft/item/ItemStack;)V") && min2.owner.equals(obfuscated ? "aay" : "net/minecraft/inventory/Slot") && min2.name.equals(obfuscated ? "c" : "putStack")) {
								mtd.instructions.insertBefore(min, new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "splitStackInSlot", obfuscated ? "(Laay;Ladd;)Z" : "(Lnet/minecraft/inventory/Slot;Lnet/minecraft/item/ItemStack;)Z", false));
								mtd.instructions.remove(min);
								
								while(iter.hasNext()) {
									AbstractInsnNode insn2 = iter.next();
									if(insn2.getOpcode() == Opcodes.GOTO) {
										insn = insn2;
										break;
									} else {
										System.out.println("remove: " + insn2.getClass().getName());
										mtd.instructions.remove(insn2);
									}
								}
								
								mtd.instructions.insertBefore(insn, new InsnNode(Opcodes.ICONST_1));
								mtd.instructions.insertBefore(insn, new VarInsnNode(Opcodes.ISTORE, 5));
								
								LabelNode skipBreak = new LabelNode();
								mtd.instructions.insertBefore(insn, new JumpInsnNode(Opcodes.IFEQ, skipBreak));
								mtd.instructions.insert(insn, skipBreak);
								
								System.out.println("[Magic and Corruption - CT: Container] success");
								byte[] ret = getNewBytes(cn);
								
//								try {
//									File out = new File("/home/okkapel/Programming/Minecraft/MagicAndCorruption/container.class");
//									DataOutputStream dos = new DataOutputStream(new FileOutputStream(out));
//									dos.write(ret);
//									dos.close();
//								} catch(Exception e) {
//									e.printStackTrace();
//								}
								
								return ret;
							}
						}
					}
				}
			}
		}
		
		System.out.println("[Magic and Corruption - CT: Container] fail");
		return classBytes;
	}

}
