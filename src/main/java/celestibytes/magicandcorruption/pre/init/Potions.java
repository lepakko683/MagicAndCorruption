package celestibytes.magicandcorruption.pre.init;

import java.lang.reflect.Constructor;

import celestibytes.magicandcorruption.pre.ASMCalls;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class Potions {
	
	private static int nextFree = 0;
	
	private static int getNextFreeId() {
		if(nextFree >= Potion.potionTypes.length) {
			ASMCalls.getAsmHooks().resizePotionArray(Potion.potionTypes.length + 32);
		}
		
		for(int i = nextFree; i < Potion.potionTypes.length; i++) {
			if(Potion.potionTypes[i] == null) {
				nextFree = i + 1;
				return i;
			}
		}
		
		return -1;
	}
	
	public static PotionCoffee potionCoffee;
	
	public static void init() {
		potionCoffee = new PotionCoffee();
	}
	
	public static abstract class PotionMco extends Potion {

		public PotionMco(boolean isBad, int color) {
			super(getNextFreeId(), isBad, color);
		}
		
	}
	
	public static class PotionCoffee extends PotionMco {

		public PotionCoffee() {
			super(false, 0xFFFF00);
		}
		
		@Override
		public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_) {
			super.performEffect(p_76394_1_, p_76394_2_);
		}
		
	}
	
}
