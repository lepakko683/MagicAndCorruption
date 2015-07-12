package celestibytes.magicandcorruption.pre.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerCraftingGuide extends Container {
	
	public ContainerCraftingGuide(EntityPlayer plr, ItemStack held) {
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer plr) {
		return true;
	}

}
