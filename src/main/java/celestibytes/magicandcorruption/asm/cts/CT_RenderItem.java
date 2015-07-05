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

public class CT_RenderItem extends ClassTransformer {

	public CT_RenderItem() {
		super("net.minecraft.client.renderer.entity.RenderItem");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated) {
		ClassReader cr = new ClassReader(classBytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		MethodNode mtd = findMethod(obfuscated ? "a" : "renderItemOverlayIntoGUI", obfuscated ? "(Lbbu;Lbqf;Ladd;IILjava/lang/String;)V" : "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", cn.methods);
		if(mtd != null) {
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.INVOKESTATIC) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.desc.equals("(I)Ljava/lang/String;") && min.name.equals("valueOf") && min.owner.equals("java/lang/String")) {
						AbstractInsnNode getField = min.getPrevious();
						if(getField.getOpcode() == Opcodes.GETFIELD) {
							FieldInsnNode fin = (FieldInsnNode) getField;
							if(fin.desc.equals("I") && fin.owner.equals(obfuscated ? "add" : "net/minecraft/item/ItemStack") && fin.name.equals(obfuscated ? "b" : "stackSize")) {
								mtd.instructions.insert(min, new MethodInsnNode(Opcodes.INVOKESTATIC, "celestibytes/magicandcorruption/pre/ASMCalls", "getCountStringForStack", obfuscated ? "(Ladd;)Ljava/lang/String;" : "(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;", false));
								mtd.instructions.remove(min);
								mtd.instructions.remove(fin);
								
								ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
								cn.accept(cw);
								
								System.out.println("[Magic and Corruption - CT: RenderItem] success");
								return cw.toByteArray();
							}
						}
					}
				}
			}
		}
		
		System.out.println("[Magic and Corruption - CT: RenderItem] fail");
		return classBytes;
	}

}
