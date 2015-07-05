package celestibytes.magicandcorruption.asm;

import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public abstract class ClassTransformer implements IClassTransformer {
	
	public final String targetClass;
	
	public ClassTransformer(String targetClassSrgName) {
		this.targetClass = targetClassSrgName;
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] basicClass) {
		if(targetClass.equals(srgName)) {
			return transform(name, srgName, basicClass, MagicAndCorruption_ASM.isObfuscatedEnv());
		}
		
		return basicClass;
	}
	
	public abstract byte[] transform(String name, String transformedName, byte[] classBytes, boolean obfuscated);
	
	protected MethodNode findMethod(String methodName, String methodDesc, List<MethodNode> methods) {
		Iterator<MethodNode> iter = methods.iterator();
		
		while(iter.hasNext()) {
			MethodNode mtd = iter.next();
			if(mtd.name.equals(methodName) && mtd.desc.equals(methodDesc)) {
				return mtd;
			}
		}
		
		return null;
	}

}
