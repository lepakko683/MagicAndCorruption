package celestibytes.magicandcorruption.pre.handler.potion;

import java.util.Arrays;
import java.util.List;

import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.init.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectCoffee extends PotionEffect {
	
	private static List<ItemStack> curativeItems = Arrays.asList(new ItemStack(ModItems.coffee));

	public PotionEffectCoffee(int duration, int amplifier, boolean ambient) {
		super(Potions.potionCoffee.id, duration, amplifier, ambient);
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase ent) {
		boolean ret = super.onUpdate(ent);
		
//		System.out.println("duration: " + getDuration());
		
		if(getDuration() == 1) {
			PotionEffect pe = new PotionEffect(Potion.digSlowdown.id, 60 * 20, 0, true);
			pe.setCurativeItems(curativeItems);
			ent.addPotionEffect(pe);
			
			pe = new PotionEffect(Potion.moveSlowdown.id, 30 * 20, 0, true);
			pe.setCurativeItems(curativeItems);
			ent.addPotionEffect(pe);
			
			return true;
		}
		
		return ret;
	}

}
