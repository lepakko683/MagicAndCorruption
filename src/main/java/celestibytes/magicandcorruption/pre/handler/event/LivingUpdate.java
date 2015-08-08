package celestibytes.magicandcorruption.pre.handler.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingUpdate {
	
	private int regl = 0;
	
	@SubscribeEvent
	public void onEvent(LivingUpdateEvent e) {
		if(e.entityLiving instanceof EntityPlayer) {
			regl++;
			if(regl >= 20 && !e.entityLiving.worldObj.isRemote) {
				ItemStack cr = ((EntityPlayer)e.entityLiving).inventory.getCurrentItem();
				if(cr != null) {
					System.out.println("Current: " + GameData.getItemRegistry().getNameForObject(cr.getItem()));
				}
				regl = 0;
			}
			
		}
	}
}
