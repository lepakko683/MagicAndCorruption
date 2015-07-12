package celestibytes.magicandcorruption.pre.handler;

import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.client.gui.GuiCraftingGuide;
import celestibytes.magicandcorruption.pre.inventory.container.ContainerCraftingGuide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer plr, World world, int x, int y, int z) {
		if(id == Ref.Guis.CRAFTING_GUIDE.ordinal()) {
			return new ContainerCraftingGuide(plr, plr.getHeldItem());
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer plr, World world, int x, int y, int z) {
		if(id == Ref.Guis.CRAFTING_GUIDE.ordinal()) {
			return new GuiCraftingGuide(plr.inventory, plr.getHeldItem());
		}
		
		return null;
	}
	
}
