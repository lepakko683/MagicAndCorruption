package celestibytes.magicandcorruption.pre.item;

import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IMcoTool {
	
	@SideOnly(Side.CLIENT)
	public Map<String, IIcon> getHeadIconsMap(ItemStack tool);
	
	public float getAttackDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase source);
	
}
