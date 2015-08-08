package celestibytes.magicandcorruption.pre.handler.event;

import celestibytes.magicandcorruption.pre.handler.extend.ExtendedPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class EntityEventH {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e) {
		if(e.entity instanceof EntityPlayer) {
			e.entity.registerExtendedProperties("McoExtendedPlayer", new ExtendedPlayer());
		}
	}
}
