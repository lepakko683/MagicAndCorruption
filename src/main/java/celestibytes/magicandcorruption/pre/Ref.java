package celestibytes.magicandcorruption.pre;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ref {
	public static final String MOD_ID = "magic_and_corruption_pre";
	public static final String MOD_NAME = "Magic And Corruption Pre";
	
	public static final String PROXY_CLIENT = "celestibytes.magicandcorruption.pre.proxy.ClientProxy";
	public static final String PROXY_SERVER = "celestibytes.magicandcorruption.pre.proxy.CommonProxy";
	
	public static final String VERSION = "0.1";
	
	public static class BlockNames {
		public static final String BLOCK_CRAFTING_TABLE = "mco_crafting_table";
		public static final String BLOCK_COMPOST = "mco_compost";
	}
	
	public static class ItemNames {
		public static final String ITEM_CRAFTING_GUIDE = "crafting_guide";
		public static final String ITEM_SWORD = "mco_sword";
		public static final String ITEM_PICKAXE = "mco_pickaxe";
		public static final String ITEM_AXE = "mco_axe";
		public static final String ITEM_SHOVEL = "mco_shovel";
		public static final String ITEM_HOE = "mco_hoe";
		public static final String ITEM_SCYTHE = "mco_scythe";
		public static final String ITEM_COFFEE = "coffee";
		public static final String BOUND_SPELL = "bound_spell";
	}
	
	public static enum Guis {
		CRAFTING_GUIDE;
	}
	
	@SideOnly(Side.CLIENT)
	public static class Textures {
		public static final ResourceLocation TEX_POTIONS = new ResourceLocation(MOD_ID, "textures/misc/potions.png");
	}
}
