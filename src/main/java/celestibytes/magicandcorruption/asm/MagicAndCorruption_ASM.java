package celestibytes.magicandcorruption.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import net.minecraft.launchwrapper.LaunchClassLoader;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class MagicAndCorruption_ASM extends DummyModContainer {
	
	private static Boolean obfEnv;
	
	public static boolean isObfuscatedEnv() { // use a different method to detect obfuscated environment
		if(obfEnv != null) {
			return obfEnv;
		}
		try {
			byte[] bts = ((LaunchClassLoader)MagicAndCorruption_ASM.class.getClassLoader()).getClassBytes("net.minecraft.world.World");
			if(bts == null) {
				obfEnv = true;
			} else {
				obfEnv = false;
			}
		} catch(Exception e) {}
		
		return obfEnv;
	}
	
	public MagicAndCorruption_ASM() {
		super(new ModMetadata());
		ModMetadata m = getMetadata();
		m.modId = "magicandcorruption_asm";
		m.name = "Magic and Corruption ASM";
		m.version = "@VERSION@";
		m.authorList = Arrays.asList(new String[]{"Okkapel"});
		m.description = "Asm tweaks for Magic and Corruption pack";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		return true;
	}
}
