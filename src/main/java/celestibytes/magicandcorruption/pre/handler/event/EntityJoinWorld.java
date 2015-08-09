package celestibytes.magicandcorruption.pre.handler.event;

import celestibytes.magicandcorruption.pre.network.NetworkHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorld {
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
		if(!e.world.isRemote && e.entity instanceof EntityPlayerMP) {
			EntityPlayerMP plr = (EntityPlayerMP) e.entity;
			NetworkHelper.syncStepHeight(plr);
			System.out.println("sync!");
		}
	}
}
