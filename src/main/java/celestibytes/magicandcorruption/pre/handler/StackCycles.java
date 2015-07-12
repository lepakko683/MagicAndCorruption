package celestibytes.magicandcorruption.pre.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackCycles {
	
	private static Map<StackIdent, ItemStack> swaps = new HashMap<StackIdent, ItemStack>();
	
	/** stack size is used to determine how many items is needed to get one of the next item,
	 *  negative values are counted as division.
	 *  If stacksize is -2 and the cycled stack has size of 3,
	 *  the active slot will be replaced by the next stack with size 1
	 *  and other items will be either put in another slot or dropped.
	 *  The cycle will not be automatically completed, so [cobble, cobble slab, cobble stair, cobble]
	 *  would be needed to create a looping cycle. */
	public static boolean registerCycle(ItemStack... cycle) {
		if(cycle == null || cycle.length == 1) {
			return false;
		}
		
		for(ItemStack st : cycle) {
			if(st.stackSize == 0) {
				st.stackSize = 1;
			}
			
			if(swaps.containsKey(new StackIdent(st))) {
				System.err.println("Intersecting cycle! Item: " + st.getItem());
				return false;
			}
		}
		
		for(int i = 0; i < cycle.length - 1; i++) {
			swaps.put(new StackIdent(cycle[i]), cycle[i + 1]);
		}
		
		return true;
	}
	
	public static ItemStack[] getNextStack(ItemStack stack, EntityPlayer plr) {
		if(stack != null) {
//			Iterator<Entry<StackIdent, ItemStack>> iter = swaps.entrySet().iterator();
//			while(iter.hasNext()) {
//				Entry<StackIdent, ItemStack> entry = iter.next();
//				System.out.println(entry.getKey().item.getUnlocalizedName() + ":" + entry.getKey().metadata + " -> " + entry.getValue().getItem().getUnlocalizedName() + ":" + entry.getValue().getItemDamage() + "~" + entry.getValue().stackSize);
//			}
			
			ItemStack next = swaps.get(new StackIdent(stack));
			if(next != null) {
				if(plr.capabilities.isCreativeMode) {
					ItemStack[] ret = new ItemStack[1];
					ret[0] = next.copy();
					ret[0].stackSize = stack.stackSize;
					
					return ret;
				}
				//System.out.println("next stacksize: " + next.stackSize);
				if(next.stackSize < 0) { // -2 = takes 2 of prev item | 2 = gives 2 items per 1 of prev item
					int newItems = stack.stackSize / Math.abs(next.stackSize);
					int oldItems = stack.stackSize % Math.abs(next.stackSize);
					
					int stacks = (int) Math.ceil((float) newItems / (float) next.getMaxStackSize());
					
					if(oldItems > 0) {
						System.out.println("A");
						stacks++;
						ItemStack[] ret = new ItemStack[stacks];
						
						for(int i = 0; i < stacks - 1; i++) {
							ItemStack st = next.copy();
							if(newItems > next.getMaxStackSize()) {
								st.stackSize = next.getMaxStackSize();
								newItems -= next.getMaxStackSize();
							} else {
								st.stackSize = newItems;
							}
							
							ret[i] = st;
						}
						
						ItemStack st = stack.copy();
						st.stackSize = oldItems;
						ret[ret.length - 1] = st;
						
						for(int j = 0; j < ret.length; j++) {
							System.out.println(j + ": " + (ret[j] == null ? "null" : ret[j].getItem().getUnlocalizedName() + " x" + ret[j].stackSize));
						}
						
						return ret;
					} else {
						System.out.println("B");
						ItemStack[] ret = new ItemStack[stacks];
						
						for(int i = 0; i < stacks; i++) {
							ItemStack st = next.copy();
							if(newItems > next.getMaxStackSize()) {
								st.stackSize = next.getMaxStackSize();
								newItems -= next.getMaxStackSize();
							} else {
								st.stackSize = newItems;
							}
							
							ret[i] = st;
						}
						
						for(int j = 0; j < ret.length; j++) {
							System.out.println(j + ": " + (ret[j] == null ? "null" : ret[j].getItem().getUnlocalizedName() + " x" + ret[j].stackSize));
						}
						
						return ret;
					}
					
					//System.out.println("new items: " + newItems + " old: " + oldItems);
				} else {
					System.out.println("C");
					int newItems = stack.stackSize * next.stackSize;
					
					int stacks = (int) Math.ceil((float) newItems / (float) next.getMaxStackSize());
					ItemStack[] ret = new ItemStack[stacks];
					for(int i = 0; i < ret.length; i++) {
						ItemStack st = next.copy();
						if(newItems > next.getMaxStackSize()) {
							st.stackSize = next.getMaxStackSize();
							newItems -= next.getMaxStackSize();
						} else {
							st.stackSize = newItems;
						}
						ret[i] = st;
					}
					
					System.out.println("new items: " + newItems);
					for(int j = 0; j < ret.length; j++) {
						System.out.println(j + ": " + (ret[j] == null ? "null" : ret[j].getItem().getUnlocalizedName() + " x" + ret[j].stackSize));
					}
					
					return ret;
				}
			}
		}
		
		System.out.println("return final");
		return new ItemStack[] {stack};
	}
	
	private static final class StackIdent {
		public final Item item;
		public final int metadata;
		
		public StackIdent(ItemStack stack) {
			item = stack.getItem();
			metadata = stack.getItemDamage();
		}

		@SuppressWarnings("unused")
		public StackIdent(Item item, int metadata) {
			this.item = item;
			this.metadata = metadata;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof StackIdent) {
				StackIdent si = (StackIdent) obj;
				return si.item == item && si.metadata == metadata;
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return (Item.getIdFromItem(item) << 16) | (metadata & 0xFFFF);
		}
	}
}
