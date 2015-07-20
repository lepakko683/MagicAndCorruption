package celestibytes.magicandcorruption.pre;

import com.jcraft.jorbis.Block;

import celestibytes.magicandcorruption.pre.crafting.RecipesTools;
import celestibytes.magicandcorruption.pre.handler.LivingUpdate;
import celestibytes.magicandcorruption.pre.handler.StackCycles;
import celestibytes.magicandcorruption.pre.init.ModBlocks;
import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.network.PacketHandler;
import celestibytes.magicandcorruption.pre.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Ref.MOD_ID, name = Ref.MOD_NAME, version = Ref.VERSION)
public class MagicAndCorruptionPre {
	
	@Instance(Ref.MOD_ID)
	public MagicAndCorruptionPre INSTANCE;
	
	@SidedProxy(clientSide = Ref.PROXY_CLIENT, serverSide = Ref.PROXY_SERVER)
	public static CommonProxy proxy;
	
	public static CreativeTabs creativeTab = new CreativeTabs(Ref.MOD_ID) {
		
		@Override
		public Item getTabIconItem() {
			return Items.ender_eye;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		ModBlocks.init();
		ModItems.init();
		
		PacketHandler.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
//		MinecraftForge.EVENT_BUS.register(new LivingUpdate());
		
		RecipesTools.init();
		
		StackCycles.registerCycle(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone_slab, 2, 3), new ItemStack(Blocks.stone_stairs, -2, 0), new ItemStack(Blocks.cobblestone));
		StackCycles.registerCycle(new ItemStack(Blocks.quartz_block), new ItemStack(Blocks.quartz_block, 1, 1), new ItemStack(Blocks.quartz_block, 1, 2), new ItemStack(Blocks.stone_slab, 2, 7), new ItemStack(Blocks.quartz_stairs, -2), new ItemStack(Blocks.quartz_block));
		StackCycles.registerCycle(new ItemStack(Blocks.nether_brick), new ItemStack(Blocks.stone_slab, 2, 6), new ItemStack(Blocks.nether_brick_stairs, -2), new ItemStack(Blocks.nether_brick));
		StackCycles.registerCycle(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick, 1, 2), new ItemStack(Blocks.stonebrick, 1, 3), new ItemStack(Blocks.stone_slab, 2, 5), new ItemStack(Blocks.stone_brick_stairs, -2), new ItemStack(Blocks.stonebrick));
		StackCycles.registerCycle(new ItemStack(Blocks.sandstone), new ItemStack(Blocks.sandstone, 1, 1), new ItemStack(Blocks.sandstone, 1, 2), new ItemStack(Blocks.stone_slab, 2, 1), new ItemStack(Blocks.sandstone_stairs, -2), new ItemStack(Blocks.sandstone));
		StackCycles.registerCycle(new ItemStack(Blocks.stone), new ItemStack(Blocks.stone_slab, 2, 0), new ItemStack(Blocks.stone, -2));
		StackCycles.registerCycle(new ItemStack(Blocks.brick_block), new ItemStack(Blocks.stone_slab, 2, 4), new ItemStack(Blocks.brick_stairs, -2), new ItemStack(Blocks.brick_block));
		
		StackCycles.registerCycle(new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.wooden_slab, 2, 0), new ItemStack(Blocks.oak_stairs, -2), new ItemStack(Blocks.planks, 1, 0));
		StackCycles.registerCycle(new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.wooden_slab, 2, 1), new ItemStack(Blocks.spruce_stairs, -2), new ItemStack(Blocks.planks, 1, 1));
		StackCycles.registerCycle(new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.wooden_slab, 2, 2), new ItemStack(Blocks.birch_stairs, -2), new ItemStack(Blocks.planks, 1, 2));
		StackCycles.registerCycle(new ItemStack(Blocks.planks, 1, 3), new ItemStack(Blocks.wooden_slab, 2, 3), new ItemStack(Blocks.jungle_stairs, -2), new ItemStack(Blocks.planks, 1, 3));
		StackCycles.registerCycle(new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.wooden_slab, 2, 4), new ItemStack(Blocks.acacia_stairs, -2), new ItemStack(Blocks.planks, 1, 4));
		StackCycles.registerCycle(new ItemStack(Blocks.planks, 1, 5), new ItemStack(Blocks.wooden_slab, 2, 5), new ItemStack(Blocks.dark_oak_stairs, -2), new ItemStack(Blocks.planks, 1, 5));
		
		proxy.registerRendering();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
}
