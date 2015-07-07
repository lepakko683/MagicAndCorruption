package celestibytes.magicandcorruption.pre;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

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
	
	public static void debugOutput(int id) { // celestibytes/magicandcorruption/ASMCalls debugOutput (I)V false
		switch(id) {
		case 0:
			System.out.println("nosend");
			break;
		case 1:
			System.out.println("sendContainerAndContentsToPlayer");
			break;
		default:
			System.out.println("unknown code: " + id);
		}
	}
}
