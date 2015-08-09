package celestibytes.magicandcorruption.pre.handler.event;

import celestibytes.magicandcorruption.pre.item.IMcoTool;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingHurt {
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e) {
		if(!e.entityLiving.worldObj.isRemote && e.source instanceof EntityDamageSource) {
			System.out.println("damage: " + e.ammount);
//			EntityDamageSource dmgs = (EntityDamageSource) e.source;
//			if(dmgs.getEntity() instanceof EntityLiving) {
//				EntityLiving attacker = (EntityLiving) dmgs.getEntity();
//				ItemStack stack = attacker.getHeldItem();
//				if(stack != null && stack.getItem() instanceof IMcoTool) {
//					e.ammount = ((IMcoTool)stack.getItem()).getAttackDamage(stack, e.entityLiving, attacker);
//					System.out.println("new damage: " + e.ammount);
//				} else {
//					System.out.println("invalid item");
//				}
//			} else if(dmgs.getEntity() instanceof EntityPlayer) {
//				EntityPlayer attacker = (EntityPlayer) dmgs.getEntity();
//				ItemStack stack = attacker.getHeldItem();
//				if(stack != null && stack.getItem() instanceof IMcoTool) {
//					e.ammount += ((IMcoTool)stack.getItem()).getAttackDamage(stack, e.entityLiving, attacker);
//					System.out.println("new damage: " + e.ammount);
//				}
//			}
		}
	}
}
