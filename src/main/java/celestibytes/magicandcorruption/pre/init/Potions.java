package celestibytes.magicandcorruption.pre.init;

import celestibytes.magicandcorruption.pre.ASMCalls;
import celestibytes.magicandcorruption.pre.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

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
	
	public static PotionMco potionCoffee;
	
	public static void init() {
		potionCoffee = new PotionCoffee();
	}
	
	public static class PotionMco extends Potion {
		
		private int iconIndex_x = 0, iconIndex_y = 0;

		public PotionMco(boolean isBad, int color) {
			super(getNextFreeId(), isBad, color);
		}
		
		public PotionMco setMcoIconIndex(int x, int y) {
			iconIndex_x = x;
			iconIndex_y = y;
			
			return this;
		}
		
		@Override
		public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
			mc.getTextureManager().bindTexture(Ref.Textures.TEX_POTIONS);
			
			x += 6;
			y += 7;
			
			float uvs = 1f / 16f;
			float uvx = ((float) iconIndex_x) / 16f;
			float uvy = ((float) iconIndex_y) / 16f;
			
			Tessellator tes = Tessellator.instance;
			tes.startDrawingQuads();
			tes.addVertexWithUV(x, y + 18, 0f, uvx, uvy + uvs);
			tes.addVertexWithUV(x + 18, y + 18, 0f, uvx + uvs, uvy + uvs);
			tes.addVertexWithUV(x + 18, y, 0f, uvx + uvs, uvy);
			tes.addVertexWithUV(x, y, 0f, uvx, uvy);
			tes.draw();
		}
	}
	
	public static class PotionCoffee extends PotionMco {

		public PotionCoffee() {
			super(false, 0xFFFF00);
			setPotionName("potion." + Ref.MOD_ID + ":coffee");
			setMcoIconIndex(0, 0);
			func_111184_a(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
		}
		
		@Override
		public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_) {
			super.performEffect(p_76394_1_, p_76394_2_);
		}
	}
}
