package celestibytes.magicandcorruption.asm.cts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;

import celestibytes.magicandcorruption.asm.ClassTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class CT_ModRemapper implements IClassTransformer {
	
	private static final Map<String, Object> dumpClasses = new HashMap<String, Object>();
	static {
		dumpClasses.put("thaumcraft.common.tiles.TileCrucible", null);
		dumpClasses.put("thaumcraft.common.blocks.BlockMetalDevice", null);
		dumpClasses.put("thaumcraft.common.lib.events.EventHandlerEntity", null);
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes) {
		if(!ModRemapper.instance.enabled || name.startsWith("net/minecraft") || name.startsWith("cpw/mods/fml")) {
			return classBytes;
		}
		
		ClassReader cr = new ClassReader(classBytes);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		RemappingClassAdapter remap = new RemappingClassAdapter(cw, ModRemapper.instance);
		cr.accept(remap, ClassReader.EXPAND_FRAMES);
		
		byte[] newBytes = cw.toByteArray();
		if(dumpClasses.containsKey(name)) {
			ClassTransformer.writeClassFile(name.replace('.', '/'), newBytes);			
		}

		return newBytes;
	}
	
	private static class ModRemapper extends Remapper {
		
		protected static ModRemapper instance = new ModRemapper();
		
		private final Map<String, String> methodNames = new HashMap<String, String>();
		private final Map<String, String> fieldNames = new HashMap<String, String>();
		
		protected boolean enabled = false;
		
		protected ModRemapper() {
			String path = System.getProperty("mco.srgMcpFilePath");
			if(path != null) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(new File(path)));
					String line = br.readLine();
					while(line != null) {
						String[] parts = line.split(" ");
						if(parts[0].equals("MD:")) {
							if(parts.length != 5) {
								br.close();
								throw new Exception("Invalid srg file format");
							}
							
							methodNames.put(parts[1].substring(parts[1].lastIndexOf("/") + 1), parts[3].substring(parts[3].lastIndexOf("/") + 1));
						} else if(parts[0].equals("FD:")) {
							if(parts.length != 3) {
								br.close();
								throw new Exception("Invalid srg file format");
							}
							
							fieldNames.put(parts[1].substring(parts[1].lastIndexOf("/") + 1), parts[2].substring(parts[2].lastIndexOf("/") + 1));
						}
						
						line = br.readLine();
					}
					
					br.close();
					enabled = true;
					System.out.println("srg file successfully loaded!");
				} catch(Exception e) {
					System.out.println("Failed to load srg file");
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public String mapMethodName(String owner, String name, String desc) {
			if(enabled) { //  && owner.startsWith("net/minecraft")
				String remap = methodNames.get(/*owner + "/" + */name);
				if(remap != null) {
					return remap;
				}
			}
			
			return name;
		}
		
		@Override
		public String mapFieldName(String owner, String name, String desc) {
			if(enabled) {
				String remap = fieldNames.get(/*owner + "/" + */name);
				if(remap != null) {
					return remap;
				}
			}
			
			return name;
		}
	}

}
