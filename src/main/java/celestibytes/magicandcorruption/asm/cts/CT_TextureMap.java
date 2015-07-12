package celestibytes.magicandcorruption.asm.cts;

import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

public class CT_TextureMap extends ClassTransformer {

	public CT_TextureMap() {
		super("net.minecraft.client.renderer.texture.TextureMap");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		
		MethodNode mtd = findMethod(obfuscated ? "b" : "loadTextureAtlas", obfuscated ? "(Lbqy;)V" : "(Lnet/minecraft/client/resources/IResourceManager;)V", cn);
		if(mtd != null) {
			boolean skipOne = true;
			Iterator<AbstractInsnNode> iter = mtd.instructions.iterator();
			while(iter.hasNext()) {
				AbstractInsnNode insn = iter.next();
				if(insn.getOpcode() == Opcodes.INVOKEINTERFACE) {
					MethodInsnNode min = (MethodInsnNode) insn;
					if(min.owner.equals("org/apache/logging/log4j/Logger") && min.name.equals("error") && min.desc.equals("(Ljava/lang/String;Ljava/lang/Throwable;)V")) {
						AbstractInsnNode prev = insn.getPrevious();
						if(prev.getOpcode() == Opcodes.ALOAD && ((VarInsnNode)prev).var == 10) {
							if(skipOne) {
								skipOne = false;
							} else {
								mtd.instructions.insertBefore(min, new InsnNode(Opcodes.ACONST_NULL));
								mtd.instructions.remove(prev);
								
								System.out.println("[Magic and Corruption - CT: TextureMap] success");
								return getNewBytes(cn);
							}
						}
					}
				}
			}
		}
		
		System.out.println("[Magic and Corruption - CT: TextureMap] failed");
		return classBytes;
	}

}
