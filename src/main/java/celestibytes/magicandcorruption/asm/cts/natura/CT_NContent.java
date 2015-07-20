package celestibytes.magicandcorruption.asm.cts.natura;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import celestibytes.magicandcorruption.asm.ClassTransformer;

// Fixes(removes TiCon integration) the PatternBuilder crash for this pack when -XX:-UseSplitVerifier is used
public class CT_NContent extends ClassTransformer {

	public CT_NContent() {
		super("mods.natura.common.NContent");
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated) {
		ClassNode cn = getClassNode(classBytes);
		
		MethodNode mtd = findMethod("modIntegration", "()V", cn);
		if(mtd != null) {
			mtd.exceptions.clear();
			mtd.tryCatchBlocks.clear();
			mtd.instructions = new InsnList();
			mtd.instructions.add(new InsnNode(Opcodes.RETURN));
			
			System.out.println("[Magic and Corruption - CT: Natura:NContent] success");
			byte[] ret = getNewBytes(cn);
			//writeClassFile("/home/okkapel/Programming/Minecraft/MagicAndCorruption/ncontent.class", ret);
			return ret;
		}
		
		System.out.println("[Magic and Corruption - CT: Natura:NContent] fail");
		return classBytes;
	}

}
