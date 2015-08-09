package celestibytes.magicandcorruption.pre.init;

import net.minecraft.item.Item;
import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.item.ItemBoundSpell;
import celestibytes.magicandcorruption.pre.item.ItemCoffee;
import celestibytes.magicandcorruption.pre.item.ItemCraftingGuide;
import celestibytes.magicandcorruption.pre.item.ItemMcoAxe;
import celestibytes.magicandcorruption.pre.item.ItemMcoHoe;
import celestibytes.magicandcorruption.pre.item.ItemMcoPickaxe;
import celestibytes.magicandcorruption.pre.item.ItemMcoShovel;
import celestibytes.magicandcorruption.pre.item.ItemMcoSword;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Ref.MOD_ID)
public class ModItems {
	
	public static ItemCraftingGuide cGuide;
	
	public static ItemMcoSword sword;
	public static ItemMcoPickaxe pickaxe;
	public static ItemMcoAxe axe;
	public static ItemMcoShovel shovel;
	public static ItemMcoHoe hoe;
	
	public static ItemCoffee coffee;
	
	public static ItemBoundSpell boundSpell;
	
	public static void init() {
		cGuide = new ItemCraftingGuide(Ref.ItemNames.ITEM_CRAFTING_GUIDE);
		
		sword = new ItemMcoSword();
		GameRegistry.registerItem(sword, Ref.ItemNames.ITEM_SWORD);
		
		pickaxe = new ItemMcoPickaxe();
		GameRegistry.registerItem(pickaxe, Ref.ItemNames.ITEM_PICKAXE);
		
		axe = new ItemMcoAxe();
		GameRegistry.registerItem(axe, Ref.ItemNames.ITEM_AXE);
		
		shovel = new ItemMcoShovel();
		GameRegistry.registerItem(shovel, Ref.ItemNames.ITEM_SHOVEL);
		
		hoe = new ItemMcoHoe();
		GameRegistry.registerItem(hoe, Ref.ItemNames.ITEM_HOE);
		
		coffee = new ItemCoffee();
		GameRegistry.registerItem(coffee, Ref.ItemNames.ITEM_COFFEE);
		
		boundSpell = new ItemBoundSpell();
		GameRegistry.registerItem(boundSpell, Ref.ItemNames.BOUND_SPELL);
	}
	
	private static void setupItem(Item item, String name) {
		item.setCreativeTab(MagicAndCorruptionPre.creativeTab);
		GameRegistry.registerItem(cGuide, name);
	}
}
