package celestibytes.magicandcorruption.pre.item;

import celestibytes.magicandcorruption.pre.Ref;
import net.minecraft.item.Item;

public class ItemCraftingGuide extends Item {
	
	public ItemCraftingGuide(String name) {
		setUnlocalizedName(name);
		setTextureName(Ref.MOD_ID + ":" + name);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item." + Ref.MOD_ID + ":" + super.getUnlocalizedName().substring(5);
	}
	
	
}
