package celestibytes.magicandcorruption.pre.item;

import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBoundSpell extends Item {
	
	public static final String CAULDRON_PROTECTION = "mco_cauldron_protect";
	
	public ItemBoundSpell() {
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName("book_enchanted");
		setUnlocalizedName(Ref.MOD_ID + ":boundSpell");
	}
	
	@Override
	public boolean hasEffect(ItemStack stack, int pass) {
		return true;
	}
	
	public ItemStack modify(ItemStack target, ItemStack spell) {
		if(target.stackTagCompound == null) {
			target.stackTagCompound = new NBTTagCompound();
		}
		
		if(!target.stackTagCompound.hasKey(CAULDRON_PROTECTION, 1)) {
			target.stackTagCompound.setBoolean(CAULDRON_PROTECTION, true);
		} else {
			return null;
		}
		
		return target;
	}
}
