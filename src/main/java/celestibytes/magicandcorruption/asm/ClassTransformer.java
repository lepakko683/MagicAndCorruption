package celestibytes.magicandcorruption.asm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
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
	
	public abstract byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated);
	
//	protected MethodNode findMethod(String methodName, String methodDesc, List<MethodNode> methods) {
//		Iterator<MethodNode> iter = methods.iterator();
//		
//		while(iter.hasNext()) {
//			MethodNode mtd = iter.next();
//			if(mtd.name.equals(methodName) && mtd.desc.equals(methodDesc)) {
//				return mtd;
//			}
//		}
//		
//		return null;
//	}
	
	public static ClassNode getClassNode(byte[] classBytes) {
		ClassReader cr = new ClassReader(classBytes);
		ClassNode ret = new ClassNode();
		cr.accept(ret, 0);
		
		return ret;
	}
	
	public static MethodNode findMethod(String methodName, String methodDesc, List<MethodNode> methods) {
		Iterator<MethodNode> iter = methods.iterator();
		
		while(iter.hasNext()) {
			MethodNode mtd = iter.next();
			if(mtd.name.equals(methodName) && mtd.desc.equals(methodDesc)) {
				return mtd;
			}
		}
		
		return null;
	}
	
	public static MethodNode findMethod(String methodName, String methodDesc, ClassNode cn) {
		Iterator<MethodNode> iter = cn.methods.iterator();
		
		while(iter.hasNext()) {
			MethodNode mtd = iter.next();
			if(mtd.name.equals(methodName) && mtd.desc.equals(methodDesc)) {
				return mtd;
			}
		}
		
		return null;
	}
	
	public static byte[] getNewBytes(ClassNode cn) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		
		return cw.toByteArray();
	}
	
	public static void writeClassFile(String fname, byte[] classBytes) {
		try {
			File out = new File(fname);
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(out));
			dos.write(classBytes);
			dos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
