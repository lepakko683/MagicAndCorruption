package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_EntityLivingBase extends ClassTransformer {

	public CT_EntityLivingBase() {
		super("net.minecraft.entity.EntityLivingBase", "EntityLivingBase");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		
		boolean injA = false;
		boolean injB = false;
		
		MethodNode mn = findMethod(obfuscated ? "b" : "getActivePotionEffect", obfuscated ? "(Lrv;)Lrw;" : "(Lnet/minecraft/potion/Potion;)Lnet/minecraft/potion/PotionEffect;", cn);
		if(mn != null) {
			InsnList ij = new InsnList();
			ij.add(new VarInsnNode(ALOAD, 1));
			ij.add(new VarInsnNode(ALOAD, 0));
			ij.add(new FieldInsnNode(GETFIELD, obfuscated ? "sv" : "net/minecraft/entity/EntityLivingBase", obfuscated ? "f" : "activePotionsMap", "Ljava/util/HashMap;"));
			ij.add(new MethodInsnNode(INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "getActivePotionEffect", obfuscated ? "(Lrv;Ljava/util/HashMap;)Lrw;" : "(Lnet/minecraft/potion/Potion;Ljava/util/HashMap;)Lnet/minecraft/potion/PotionEffect;", false));
			ij.add(new InsnNode(ARETURN));
			
			mn.instructions = ij;
			injA = true;
		}
		
		mn = findMethod(obfuscated ? "j" : "getArmSwingAnimationEnd", "()I", cn);
		if(mn != null) {
			Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
			while (iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.owner.equals(obfuscated ? "sv" : "net/minecraft/entity/EntityLivingBase") && min.name.equals(obfuscated ? "a" : "isPotionActive") && min.desc.equals(obfuscated ? "(Lrv;)Z" : "(Lnet/minecraft/potion/Potion;)Z")) {
						InsnList ij = new InsnList();
						ij.add(new VarInsnNode(ALOAD, 0));
						ij.add(new FieldInsnNode(GETFIELD, obfuscated ? "sv" : "net/minecraft/entity/EntityLivingBase", obfuscated ? "f" : "activePotionsMap", "Ljava/util/HashMap;"));
						ij.add(new MethodInsnNode(INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "isPotionActive", obfuscated ? "(Lrv;Ljava/util/HashMap;)Z" : "(Lnet/minecraft/potion/Potion;Ljava/util/HashMap;)Z", false));
						
						mn.instructions.insert(min, ij);
						mn.instructions.remove(min);
						injB = true;
						break;
					}
				}
			}
		}
		
		if(injA && injB) {
			return getNewBytesLog(cn);
		}
		
		return returnFail(classBytes);
	}

}
