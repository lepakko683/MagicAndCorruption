package celestibytes.magicandcorruption.pre.init;

import net.minecraft.block.Block;
import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.block.BlockMcoCTable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Ref.MOD_ID)
public class ModBlocks {
	
	public static BlockMcoCTable mcoCraftingTable;
	
	public static void init() {
		mcoCraftingTable = new BlockMcoCTable("mco_crafting_table");
		setupBlock(mcoCraftingTable, "mco_crafting_table");
	}
	
	private static void setupBlock(Block blc, String name) {
		blc.setCreativeTab(MagicAndCorruptionPre.creativeTab);
		
		GameRegistry.registerBlock(blc, name);
	}
}
