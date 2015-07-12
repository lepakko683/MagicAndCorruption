package celestibytes.magicandcorruption.pre.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMcoCTable extends ItemBlock {

	public ItemMcoCTable(Block block) {
		super(block);
	}
	
	// Overrides getNextItemInCycle in Item - added by CT_Item
	public ItemStack[] getNextItemInCycle(ItemStack stack, World world, EntityPlayer plr) {
		if(!world.isRemote) {
			System.out.println("call!! in item, server");
			if(stack.stackSize >= 100) {
				stack.stackSize -= 100;
				return new ItemStack[] {stack, new ItemStack(Items.nether_star)};
			}
		}
		return null;
	}

}
