package celestibytes.magicandcorruption.pre.client.gui;

import celestibytes.magicandcorruption.pre.inventory.container.ContainerCraftingGuide;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GuiCraftingGuide extends GuiContainer {

	public GuiCraftingGuide(InventoryPlayer plrInv, ItemStack held) {
		super(new ContainerCraftingGuide(plrInv.player, held));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partTick, int mx, int my) {
		
	}

}
