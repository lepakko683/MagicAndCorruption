package celestibytes.magicandcorruption.pre.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	
	private static final ItemStack defaultCure = new ItemStack(Items.milk_bucket);
	
	public boolean hasCustomCures(List list) {
		if(list.size() == 1 && defaultCure.isItemEqual((ItemStack)list.get(0))) {
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public NBTTagCompound writeStackListToNBT(NBTTagCompound nbt, List list) {
		int i = 0;
		
		for(Object o : list) {
			ItemStack stack = (ItemStack) o;
			NBTTagCompound tag = new NBTTagCompound();
			stack.writeToNBT(tag);
			nbt.setTag(String.valueOf(i), tag);
			i++;
		}
		
		return nbt;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List readStackListToNBT(NBTTagCompound nbt) {
		List ret = new ArrayList();
		int i = 0;
		
		while(nbt.hasKey(String.valueOf(i))) {
			ItemStack load = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag(String.valueOf(i)));
			if(load != null) {
				ret.add(load);
			}
			
			i++;
		}
		
		return ret;
	}
}
