package celestibytes.magicandcorruption.pre.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
}
