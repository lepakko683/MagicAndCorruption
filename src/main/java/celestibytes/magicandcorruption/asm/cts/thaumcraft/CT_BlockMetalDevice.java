package celestibytes.magicandcorruption.asm.cts.thaumcraft;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_BlockMetalDevice extends ClassTransformer {

	public CT_BlockMetalDevice() {
		super("thaumcraft.common.blocks.BlockMetalDevice", "Thaumcraft: BlockMetalDevice");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		
		MethodNode mn = findMethod(obfuscated ? "func_149670_a" : "onEntityCollidedWithBlock", "(Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)V", cn);
		if(mn != null) {
			Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
			while (iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == INVOKEVIRTUAL) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.owner.equals("thaumcraft/common/tiles/TileCrucible") && min.name.equals("attemptSmelt")) {
						AbstractInsnNode chCast = min.getPrevious();
						if(chCast != null && chCast.getOpcode() == CHECKCAST) {
							InsnList inj = new InsnList();
							inj.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/entity/item/EntityItem", obfuscated ? "func_92059_d" : "getEntityItem", "()Lnet/minecraft/item/ItemStack;", false));
							inj.add(new MethodInsnNode(INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "hasCauldronProtection", "(Lnet/minecraft/item/ItemStack;)Z", false));
							LabelNode l0 = new LabelNode();
							inj.add(new JumpInsnNode(IFNE, l0));
							inj.add(new VarInsnNode(ALOAD, 5));
							inj.add(new TypeInsnNode(CHECKCAST, "net/minecraft/entity/item/EntityItem"));
							
							LabelNode l1 = new LabelNode(); // TODO: cleanup!
							
							mn.instructions.insert(chCast, inj);
							mn.instructions.insert(min, l1);
							mn.instructions.insert(min, new InsnNode(POP));
							mn.instructions.insert(min, l0);
							mn.instructions.insert(min, new JumpInsnNode(GOTO, l1));
							
							return getNewBytesLog(cn);
						}
					}
				}
			}
		}
		return returnFail(classBytes);
	}

}
