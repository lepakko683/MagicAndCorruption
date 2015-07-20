package celestibytes.magicandcorruption.pre.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockPos {
	public int x, y, z;
	
	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean isAirAt(World world) {
		return world.isAirBlock(x, y, z);
	}
	
	public Block getBlockAt(World world) {
		return world.getBlock(x, y, z);
	}
	
	public int getMetadataAt(World world) {
		return world.getBlockMetadata(x, y, z);
	}
	
	public void setBlockAt(World world, Block block) {
		world.setBlock(x, y, z, block);
	}
	
	public void setBlockAt(World world, Block block, int metadata, boolean blockUpdate) {
		world.setBlock(x, y, z, block, metadata, blockUpdate ? 3 : 2);
	}
	
	public void setBlockMetadataAt(World world, int metadata, boolean blockUpdate) {
		world.setBlockMetadataWithNotify(x, y, z, metadata, blockUpdate ? 3 : 2);
	}
	
}
