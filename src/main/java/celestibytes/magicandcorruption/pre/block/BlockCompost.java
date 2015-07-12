package celestibytes.magicandcorruption.pre.block;

import cpw.mods.fml.common.registry.GameRegistry;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.block.tileentity.TECompost;
import celestibytes.magicandcorruption.pre.client.render.RenderCompost;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCompost extends Block implements ITileEntityProvider {
	
	public static final int COMPOST_MAX_WIDTH = 3; // 1.25 blocks tall

	public BlockCompost(String name) {
		super(Material.wood);
		setBlockName(name);
		setBlockTextureName(Ref.MOD_ID + ":" + name);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0f, 0f, 0f, 1f, 1.25f, 1f);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + Ref.MOD_ID + ":" + super.getUnlocalizedName().substring(5);
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderCompost.s_getRenderId();
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TECompost te = getCompostTE(world, x, y, z);
		TECompost changed = getCompostTE(world, tileX, tileY, tileZ);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TECompost();
	}
	
	private TECompost getCompostTE(IBlockAccess world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		return (te != null && te instanceof TECompost) ? (TECompost) te : null;
	}
}
