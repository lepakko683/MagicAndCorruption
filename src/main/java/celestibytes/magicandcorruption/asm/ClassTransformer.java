package celestibytes.magicandcorruption.asm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public abstract class ClassTransformer implements IClassTransformer, Opcodes {
	// -Dmco.bytecodeDumpPath=<path to a directory> to enable bytecode dumping
	
	private static Boolean dumpBytecode = null;
	private static File bytecodeDumpDir = null;
	
	public final String targetClass;
	
	protected String ctName = "Unnamed_CT";
	
	public ClassTransformer(String targetClassSrgName) {
		this.targetClass = targetClassSrgName;
	}
	
	public ClassTransformer(String targetClassSrgName, String ctName) {
		this(targetClassSrgName);
		this.ctName = ctName;
	}

	@Override
	public byte[] transform(String name, String srgName, byte[] basicClass) {
		if(targetClass.equals(srgName)) {
			return transform(name, srgName, basicClass, MagicAndCorruption_ASM.isObfuscatedEnv());
		}
		
		return basicClass;
	}
	
	public void printStatus(String status) {
		System.out.println(getPrintRoot() + status); // TODO: use logger
	}
	
	public String getPrintRoot() {
		return "[Magic and Corruption - CT:" + getCTName() + "] ";
	}
	
	public void setCTName(String name) {
		this.ctName = name;
	}
	
	public String getCTName() {
		return ctName;
	}
	
	public abstract byte[] transform(String name, String srgName, byte[] classBytes, boolean obfuscated);
	
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
	
	public byte[] returnFail(byte[] classBytes) {
		printStatus("fail");
		return classBytes;
	}
	
	public byte[] getNewBytesLog(ClassNode cn) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		
		printStatus("success");
		
		byte[] ret = cw.toByteArray();
		if(shouldDumpBytecode()) {
			writeClassFile(cn.name, ret);
		}
		
		return ret;
	}
	
	public static byte[] getNewBytes(ClassNode cn) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		
		byte[] ret = cw.toByteArray();
		if(shouldDumpBytecode()) {
			writeClassFile(cn.name, ret);
		}
		
		return ret;
	}
	
	public static void writeClassFile(String fname, byte[] classBytes) {
		if(!shouldDumpBytecode()) {
			return;
		}
		
		try {
			File outDir;
			if(fname.indexOf("/") != -1) {
				outDir = new File(bytecodeDumpDir, fname.substring(0, fname.lastIndexOf("/")));
			} else {
				outDir = bytecodeDumpDir;
			}
			
			outDir.mkdirs();
			
			File out = new File(bytecodeDumpDir, fname.concat(".class"));
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(out));
			dos.write(classBytes);
			dos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean shouldDumpBytecode() {
		if(dumpBytecode == null) {
			String path = System.getProperty("mco.bytecodeDumpPath", null);
			
			dumpBytecode = false;
			if(path != null) {
				File bcDir = new File(path);
				if(!bcDir.exists()) {
					System.out.println("Error: Bytecode dump directory doesn't exist!");
				} else if(bcDir.isFile()) {
					System.out.println("Error: Bytecode dump path points to a file, not a directory!");
				} else {
					bytecodeDumpDir = bcDir;
					dumpBytecode = true;
				}
			}
		}
		
		return dumpBytecode;
	}
	
	public static File getBytecodeDir() {
		if(shouldDumpBytecode()) {
			return bytecodeDumpDir;
		}
		
		return null;
	}

}
