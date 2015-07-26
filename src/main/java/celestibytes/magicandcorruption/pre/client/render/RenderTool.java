package celestibytes.magicandcorruption.pre.client.render;

import java.util.List;

import celestibytes.magicandcorruption.pre.item.IMcoTool;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderTool implements IItemRenderer {
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(!item.hasTagCompound()) {
			return false;
		}
		
		switch(type) {
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case INVENTORY:
			return true;
		case FIRST_PERSON_MAP:
			return false;
		}
		
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if(!(stack.getItem() instanceof IMcoTool)) {
			return;
		}
		
		List<IIcon> headIcons = ((IMcoTool)stack.getItem()).getHeadIcons(stack);
	}

}
