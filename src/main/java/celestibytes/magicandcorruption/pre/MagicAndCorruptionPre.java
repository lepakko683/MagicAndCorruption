package celestibytes.magicandcorruption.pre;

import celestibytes.magicandcorruption.pre.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Ref.MOD_ID, name = Ref.MOD_NAME, version = Ref.VERSION)
public class MagicAndCorruptionPre {
	
	@Instance(Ref.MOD_ID)
	public MagicAndCorruptionPre INSTANCE;
	
	public static CreativeTabs creativeTab = new CreativeTabs(Ref.MOD_ID) {
		@Override
		public Item getTabIconItem() {
			return Items.ender_eye;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		ModBlocks.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	
	}
	
}
