package celestibytes.magicandcorruption.pre.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.block.BlockCompost;
import celestibytes.magicandcorruption.pre.block.BlockMcoCTable;
import celestibytes.magicandcorruption.pre.block.tileentity.TECompost;
import celestibytes.magicandcorruption.pre.item.ItemMcoCTable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Ref.MOD_ID)
public class ModBlocks {
	
	public static BlockMcoCTable mcoCraftingTable;
	public static BlockCompost compost;
	
	public static void init() {
		mcoCraftingTable = new BlockMcoCTable(Ref.BlockNames.BLOCK_CRAFTING_TABLE);
		setupBlock(mcoCraftingTable, ItemMcoCTable.class, Ref.BlockNames.BLOCK_CRAFTING_TABLE);
		
		compost = new BlockCompost(Ref.BlockNames.BLOCK_CRAFTING_TABLE);
		setupBlock(compost, Ref.BlockNames.BLOCK_COMPOST);
		GameRegistry.registerTileEntity(TECompost.class, Ref.BlockNames.BLOCK_COMPOST);
	}
	
	private static void setupBlock(Block blc, String name) {
		blc.setCreativeTab(MagicAndCorruptionPre.creativeTab);
		
		GameRegistry.registerBlock(blc, name);
	}
	
	private static void setupBlock(Block blc, Class<? extends ItemBlock> itemBlock, String name) {
		blc.setCreativeTab(MagicAndCorruptionPre.creativeTab);
		
		GameRegistry.registerBlock(blc, itemBlock, name);
	}
}
