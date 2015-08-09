package celestibytes.magicandcorruption.pre.crafting;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.McoToolMaterial;
import celestibytes.magicandcorruption.pre.handler.ToolHelper;
import celestibytes.magicandcorruption.pre.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;

public class RecipesConvert implements IRecipe {
	
	private static class RecipeMatcher {
		
		private final String headMat;
		private final Item toolType;
		private final ItemStack result;
		
		public RecipeMatcher(String headMat, Item toolType, ItemStack result) {
			this.headMat = headMat;
			this.toolType = toolType;
			this.result = result;
		}
		
		public boolean matches(ItemStack stack) {
			if(stack.getItem() == toolType) {
				McoToolMaterial mat = ToolHelper.getHeadMaterial(stack);
				if(mat != null && headMat.equals(mat.modId)) {
					return true;
				}
			}
			
			return false;
		}
		
		public ItemStack getResult() {
			return result.copy();
		}
	}
	
	private static List<RecipeMatcher> matchers = new LinkedList<RecipeMatcher>();
	
	static {
		matchers.add(new RecipeMatcher("sword_head_wood", ModItems.sword, new ItemStack(Items.wooden_sword)));
		matchers.add(new RecipeMatcher("pick_head_wood", ModItems.pickaxe, new ItemStack(Items.wooden_pickaxe)));
		matchers.add(new RecipeMatcher("axe_head_wood", ModItems.axe, new ItemStack(Items.wooden_axe)));
		matchers.add(new RecipeMatcher("shovel_head_wood", ModItems.shovel, new ItemStack(Items.wooden_shovel)));
		matchers.add(new RecipeMatcher("hoe_head_wood", ModItems.hoe, new ItemStack(Items.wooden_hoe)));
		
		matchers.add(new RecipeMatcher("sword_head_stone", ModItems.sword, new ItemStack(Items.stone_sword)));
		matchers.add(new RecipeMatcher("pick_head_stone", ModItems.pickaxe, new ItemStack(Items.stone_pickaxe)));
		matchers.add(new RecipeMatcher("axe_head_stone", ModItems.axe, new ItemStack(Items.stone_axe)));
		matchers.add(new RecipeMatcher("shovel_head_stone", ModItems.shovel, new ItemStack(Items.stone_shovel)));
		matchers.add(new RecipeMatcher("hoe_head_stone", ModItems.hoe, new ItemStack(Items.stone_hoe)));
		
		matchers.add(new RecipeMatcher("sword_head_iron", ModItems.sword, new ItemStack(Items.iron_sword)));
		matchers.add(new RecipeMatcher("pick_head_iron", ModItems.pickaxe, new ItemStack(Items.iron_pickaxe)));
		matchers.add(new RecipeMatcher("axe_head_iron", ModItems.axe, new ItemStack(Items.iron_axe)));
		matchers.add(new RecipeMatcher("shovel_head_iron", ModItems.shovel, new ItemStack(Items.iron_shovel)));
		matchers.add(new RecipeMatcher("hoe_head_iron", ModItems.hoe, new ItemStack(Items.iron_hoe)));
		
		matchers.add(new RecipeMatcher("sword_head_gold", ModItems.sword, new ItemStack(Items.golden_sword)));
		matchers.add(new RecipeMatcher("pick_head_gold", ModItems.pickaxe, new ItemStack(Items.golden_pickaxe)));
		matchers.add(new RecipeMatcher("axe_head_gold", ModItems.axe, new ItemStack(Items.golden_axe)));
		matchers.add(new RecipeMatcher("shovel_head_gold", ModItems.shovel, new ItemStack(Items.golden_shovel)));
		matchers.add(new RecipeMatcher("hoe_head_gold", ModItems.hoe, new ItemStack(Items.golden_hoe)));
		
		matchers.add(new RecipeMatcher("sword_head_diamond", ModItems.sword, new ItemStack(Items.diamond_sword)));
		matchers.add(new RecipeMatcher("pick_head_diamond", ModItems.pickaxe, new ItemStack(Items.diamond_pickaxe)));
		matchers.add(new RecipeMatcher("axe_head_diamond", ModItems.axe, new ItemStack(Items.diamond_axe)));
		matchers.add(new RecipeMatcher("shovel_head_diamond", ModItems.shovel, new ItemStack(Items.diamond_shovel)));
		matchers.add(new RecipeMatcher("hoe_head_diamond", ModItems.hoe, new ItemStack(Items.diamond_hoe)));
	}
	
	private RecipeMatcher match;

	@Override
	public boolean matches(InventoryCrafting cinv, World world) {
		ItemStack stack = null;
		for(int i = 0; i < cinv.getSizeInventory(); i++) {
			ItemStack is = cinv.getStackInSlot(i);
			if(is != null) {
				if(stack != null) {
					return false;
				}
				
				stack = is;
			}
		}
		
		if(stack != null) {
			for(RecipeMatcher rm : matchers) {
				if(rm.matches(stack)) {
					match = rm;
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting cinv) {
		return match.getResult();
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	public static void init() {
		RecipeSorter.register("mco_recipes_convert", RecipesConvert.class, RecipeSorter.Category.SHAPELESS, "");
		GameRegistry.addRecipe(new RecipesConvert());
	}
}
