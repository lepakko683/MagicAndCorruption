package celestibytes.magicandcorruption.pre.item;

import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCoffee extends Item {
	
	public ItemCoffee() {
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName(Ref.MOD_ID + ":coffee");
		setMaxStackSize(16);
		setUnlocalizedName(Ref.MOD_ID + ":coffee");
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.drink;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer plr) {
		plr.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer plr) {
		if(!plr.capabilities.isCreativeMode) {
			stack.stackSize--;
		}
		
		if(!world.isRemote) {
			plr.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 20 * 30, 0, true));
			plr.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * 30, 0, true));
		}
		
		return stack;
	}
	
}
