package celestibytes.magicandcorruption.pre.crafting;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.init.PackItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class NormalRecipes {
	
	public static void init() {
		GameRegistry.addRecipe(new ItemStack(ModItems.boundSpell), 
				" E ", "RBR", " E ",
				'E', new ItemStack(PackItems.Thaumcraft.itemResource, 1, 7),
				'R', new ItemStack(PackItems.Witchery.itemIngredient, 1, 63),
				'B', Items.book);
		GameRegistry.addRecipe(new ItemStack(ModItems.boundSpell), 
				" E ", "RBR", " E ",
				'R', new ItemStack(PackItems.Thaumcraft.itemResource, 1, 7),
				'E', new ItemStack(PackItems.Witchery.itemIngredient, 1, 63),
				'B', Items.book);
	}
	
	private static List<ItemStack> removeRecipes = new LinkedList<ItemStack>();
	
	@SuppressWarnings("rawtypes")
	public static void doRecipeTweaks() {
		removeRecipes.add(new ItemStack(Items.wooden_sword));
		removeRecipes.add(new ItemStack(Items.wooden_pickaxe));
		removeRecipes.add(new ItemStack(Items.wooden_shovel));
		removeRecipes.add(new ItemStack(Items.wooden_axe));
		removeRecipes.add(new ItemStack(Items.wooden_hoe));
		
		removeRecipes.add(new ItemStack(Items.stone_sword));
		removeRecipes.add(new ItemStack(Items.stone_pickaxe));
		removeRecipes.add(new ItemStack(Items.stone_shovel));
		removeRecipes.add(new ItemStack(Items.stone_axe));
		removeRecipes.add(new ItemStack(Items.stone_hoe));
		
		removeRecipes.add(new ItemStack(Items.iron_sword));
		removeRecipes.add(new ItemStack(Items.iron_pickaxe));
		removeRecipes.add(new ItemStack(Items.iron_shovel));
		removeRecipes.add(new ItemStack(Items.iron_axe));
		removeRecipes.add(new ItemStack(Items.iron_hoe));
		
		removeRecipes.add(new ItemStack(Items.golden_sword));
		removeRecipes.add(new ItemStack(Items.golden_pickaxe));
		removeRecipes.add(new ItemStack(Items.golden_shovel));
		removeRecipes.add(new ItemStack(Items.golden_axe));
		removeRecipes.add(new ItemStack(Items.golden_hoe));
		
		removeRecipes.add(new ItemStack(Items.diamond_sword));
		removeRecipes.add(new ItemStack(Items.diamond_pickaxe));
		removeRecipes.add(new ItemStack(Items.diamond_shovel));
		removeRecipes.add(new ItemStack(Items.diamond_axe));
		removeRecipes.add(new ItemStack(Items.diamond_hoe));
		
		List recipes = CraftingManager.getInstance().getRecipeList();
		for(Iterator iter = recipes.iterator(); iter.hasNext();) {
			IRecipe rc = (IRecipe) iter.next();
			ItemStack out = rc.getRecipeOutput();
			if(out != null && listContains(removeRecipes, out)) {
				iter.remove();
			}
		}
	}
	
	private static boolean listContains(List<ItemStack> list, ItemStack obj) {
		for(ItemStack is : list) {
			if(ItemStack.areItemStacksEqual(is, obj)) {
				return true;
			}
		}
		
		return false;
	}
}
