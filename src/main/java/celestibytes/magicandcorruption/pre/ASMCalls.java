package celestibytes.magicandcorruption.pre;

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
}
