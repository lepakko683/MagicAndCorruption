package celestibytes.magicandcorruption.pre.block;

import celestibytes.magicandcorruption.pre.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMcoCTable extends Block {
	
	public BlockMcoCTable(String name) {
		super(Material.wood);
		setBlockName(name);
		setBlockTextureName(Ref.MOD_ID + ":" + name);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + Ref.MOD_ID + ":" + super.getUnlocalizedName().substring(5);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		super.registerBlockIcons(p_149651_1_);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.crafting_table.getIcon(side, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer plr, int side, float hitX, float hitY, float hitZ) {
		return super.onBlockActivated(world, x, y, z, plr, side, hitX, hitY, hitZ);
	}

}
