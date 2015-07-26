package celestibytes.magicandcorruption.pre.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IMcoTool {
	
	@SideOnly(Side.CLIENT)
	public List<IIcon> getHeadIcons(ItemStack tool);
	
}
