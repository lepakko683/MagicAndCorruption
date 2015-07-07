package celestibytes.magicandcorruption.pre.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TEMcoCTable extends TileEntity implements IInventory {
	
	private ItemStack[] stacks = new ItemStack[9];

	@Override
	public void updateEntity() {
		boolean spam = false;
		
		if(spam) {
			System.out.println("update");
		}
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return i >= 0 && i <= getSizeInventory() ? stacks[i] : null;
	}

	@Override
	public ItemStack decrStackSize(int i, int n) {
		if(i >= 0 && i <= getSizeInventory()) {
			if(stacks[i] != null) {
				ItemStack ret;
				if(stacks[i].stackSize <= n) {
					ret = stacks[i];
					stacks[i] = null;
					markDirty();
				} else {
					ret = stacks[i].splitStack(n);
					markDirty();
				}
				
				return ret;
			}
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(i >= 0 && i <= getSizeInventory()) {
			if(stacks[i] != null) {
				ItemStack ret = stacks[i];
				stacks[i] = null;
				return ret;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		if(i >= 0 && i <= getSizeInventory()) {
			stacks[i] = stack;
		}
	}

	@Override
	public String getInventoryName() {
		return "Crafting Table";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer plr) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : 
			plr.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}
	
}
