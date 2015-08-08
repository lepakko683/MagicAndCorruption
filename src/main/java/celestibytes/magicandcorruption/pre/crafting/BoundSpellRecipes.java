package celestibytes.magicandcorruption.pre.crafting;

import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.item.ItemBoundSpell;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class BoundSpellRecipes implements IRecipe {
	
	private ItemStack target;
	private ItemStack spell;

	@Override
	public boolean matches(InventoryCrafting cinv, World world) {
		target = null;
		spell = null;
		
		for(int i = 0; i < cinv.getSizeInventory(); i++) {
			ItemStack is = cinv.getStackInSlot(i);
			if(is != null) {
				if(is.getItem() == ModItems.boundSpell) {
					if(spell != null) {
						return false;
					}
					
					spell = is;
				} else {
					if(target != null) {
						return false;
					}
					
					target = is;
				}
			}
		}
		
		return target != null && spell != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting cinv) {
		if(target != null && spell != null && spell.getItem() == ModItems.boundSpell) {
			return ((ItemBoundSpell) spell.getItem()).modify(target, spell);
		}
			
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
