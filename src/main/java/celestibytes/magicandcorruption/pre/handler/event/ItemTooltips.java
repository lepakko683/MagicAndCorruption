package celestibytes.magicandcorruption.pre.handler.event;

import celestibytes.magicandcorruption.pre.item.ItemBoundSpell;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemTooltips {

	@SubscribeEvent
	public void onItemTooltipEvent(ItemTooltipEvent e) {
		if(e.itemStack.stackTagCompound != null) {
			if(e.itemStack.stackTagCompound.getBoolean(ItemBoundSpell.CAULDRON_PROTECTION)) {
				e.toolTip.add(EnumChatFormatting.DARK_GRAY + "Has cauldron protection");
			}
		}
	}
	
}
