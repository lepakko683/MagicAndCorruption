package celestibytes.magicandcorruption.pre;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;

@Mod(modid = Ref.MOD_ID, name = Ref.MOD_NAME, version = Ref.VERSION)
public class MagicAndCorruptionPre {
	
	@Instance(Ref.MOD_ID)
	public MagicAndCorruptionPre INSTANCE;
	
	public CreativeTabs creativeTab = new CreativeTabs("Magic and Corruption") {
		@Override
		public Item getTabIconItem() {
			return Items.ender_eye;
		}
	};
	
}
