package celestibytes.magicandcorruption.pre;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ASMCalls {
	
	public static String getCountStringForStack(ItemStack stack) {
		if(stack.stackSize == 666) {
			return EnumChatFormatting.RED + String.valueOf(stack.stackSize);
		} else if(stack.stackSize == 1000) {
			return EnumChatFormatting.AQUA + "1K";
		}
		
		if(stack.stackSize < 64) {
			return String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 128) {
			return EnumChatFormatting.LIGHT_PURPLE + String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 256) {
			return EnumChatFormatting.BLUE + String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 512) {
			return EnumChatFormatting.GOLD + String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 1000) {
			return EnumChatFormatting.GREEN + String.valueOf(stack.stackSize);
		}
		
		return String.valueOf(stack.stackSize);
	}
	
	/** returns false if the provided stack got empty and therefore we can "break" out of the loop */
	public static boolean splitStackInSlot(Slot slot, ItemStack stack) {
		int slotLimit = slot.getSlotStackLimit();
		if(stack.stackSize > slotLimit) {
			slot.putStack(stack.splitStack(slotLimit));
			slot.onSlotChanged();
		} else {
			slot.putStack(stack.copy());
			slot.onSlotChanged();
			stack.stackSize = 0;
			
			return true;
		}
		
		return false;
	}
	
	public static void handleCycleExtraItems(ItemStack[] stacks, World world, EntityPlayer plr) {
		System.out.println("Call!");
		if(stacks.length > 1) {
			for(int i = 1; i < stacks.length; i++) {
				if(stacks[i] != null) {
					System.out.println("extra stack size: " + stacks[i].stackSize + ", name: " + stacks[i].getDisplayName() + ", meta: " + stacks[i].getItemDamage());
				}
				//if(!plr.inventory.addItemStackToInventory(stacks[i].copy())) {
				if(!addExtraStack(stacks[i].copy(), plr)) {
					EntityItem item = new EntityItem(world, plr.posX, plr.posY + plr.eyeHeight/2f, plr.posZ, stacks[i]);
					world.spawnEntityInWorld(item);
				}
			}
		}
	}
	
	private static boolean addExtraStack(ItemStack stack, EntityPlayer plr) { // TODO!
		InventoryPlayer inv = plr.inventory;
		
		for(int i = 0; i < inv.mainInventory.length; i++) {
			if(i != inv.currentItem) {
				ItemStack is = inv.mainInventory[i];
				if(is != null) {
					if(is.getItem() == stack.getItem() && is.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStacksEqual(is, stack)) {
						int space = Math.min(is.getMaxStackSize(), inv.getInventoryStackLimit()) - is.stackSize;
						if(space > 0) {
							int add = Math.min(space, stack.stackSize);
							stack.stackSize -= add;
							is.stackSize += add;
							
							if(stack.stackSize <= 0) {
								return true;
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < inv.mainInventory.length; i++) {
			if(i != inv.currentItem) {
				if(inv.mainInventory[i] == null) {
					int put = Math.min(stack.stackSize, inv.getInventoryStackLimit());
					inv.mainInventory[i] = stack.splitStack(put);
					
					if(stack.stackSize <= 0) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static void debugOutput(int id) { // celestibytes/magicandcorruption/ASMCalls debugOutput (I)V false
		switch(id) {
		case 0:
			System.out.println("nosend");
			break;
		case 1:
			System.out.println("sendContainerAndContentsToPlayer");
			break;
		case 2:
			System.out.println("stepassist on");
			break;
		case 3:
			System.out.println("stepassist off");
			break;
		default:
			System.out.println("unknown code: " + id);
		}
	}
}
