package celestibytes.magicandcorruption.pre.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderCompost implements ISimpleBlockRenderingHandler {
	
	private static int ID = 0;
	
	public static int s_getRenderId() { return ID; }
	
	public RenderCompost() {
		ID = RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		//IIcon icon = block.getIcon(0, 0);
		IIcon icon = Blocks.planks.getIcon(0, 0);
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		
		tes.setBrightness(0xF000F0);
		tes.setColorOpaque_F(1.0f, 1.0f, 1.0f);
		
		renderCompost(tes, icon, 0f, 0f, 0f);
		
		tes.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		//IIcon icon = block.getIcon(0, 0);
		IIcon icon = Blocks.planks.getIcon(0, 0);
		Tessellator tes = Tessellator.instance;
		
		tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        
        renderCompost(tes, icon, x, y, z);
        
		return true;
	}
	
	private void renderCompost(Tessellator tes, IIcon i, float x, float y, float z) {
		// North side
		tes.addVertexWithUV(x,      y +  1.25f, z, i.getMaxU(), i.getMinV());
		tes.addVertexWithUV(x + 1f, y +  1.25f, z, i.getMinU(), i.getMinV());
		tes.addVertexWithUV(x + 1f, y + .9375f, z, i.getMinU(), i.getInterpolatedV(5f));
		tes.addVertexWithUV(x,      y + .9375f, z, i.getMaxU(), i.getInterpolatedV(5f));
		
		tes.addVertexWithUV(x,      y + .8125f, z, i.getMaxU(), i.getInterpolatedV(5f));
		tes.addVertexWithUV(x + 1f, y + .8125f, z, i.getMinU(), i.getInterpolatedV(5f));
		tes.addVertexWithUV(x + 1f, y + .4375f, z, i.getMinU(), i.getInterpolatedV(11f));
		tes.addVertexWithUV(x,      y + .4375f, z, i.getMaxU(), i.getInterpolatedV(11f));
		
		tes.addVertexWithUV(x,      y + .3125f, z, i.getMaxU(), i.getInterpolatedV(11f));
		tes.addVertexWithUV(x + 1f, y + .3125f, z, i.getMinU(), i.getInterpolatedV(11f));
		tes.addVertexWithUV(x + 1f, y         , z, i.getMinU(), i.getMaxV());
		tes.addVertexWithUV(x,      y         , z, i.getMaxU(), i.getMaxV());
		
		// South side
		tes.addVertexWithUV(x + 1f, y +  1.25f, z + 1f, i.getMaxU(), i.getMinV());
		tes.addVertexWithUV(x     , y +  1.25f, z + 1f, i.getMinU(), i.getMinV());
		tes.addVertexWithUV(x     , y + .9375f, z + 1f, i.getMinU(), i.getInterpolatedV(5f));
		tes.addVertexWithUV(x + 1f, y + .9375f, z + 1f, i.getMaxU(), i.getInterpolatedV(5f));
		
		tes.addVertexWithUV(x + 1f, y + .8125f, z + 1f, i.getMaxU(), i.getInterpolatedV(5f));
		tes.addVertexWithUV(x     , y + .8125f, z + 1f, i.getMinU(), i.getInterpolatedV(5f));
		tes.addVertexWithUV(x     , y + .4375f, z + 1f, i.getMinU(), i.getInterpolatedV(11f));
		tes.addVertexWithUV(x + 1f, y + .4375f, z + 1f, i.getMaxU(), i.getInterpolatedV(11f));
		
		tes.addVertexWithUV(x + 1f, y + .3125f, z + 1f, i.getMaxU(), i.getInterpolatedV(11f));
		tes.addVertexWithUV(x     , y + .3125f, z + 1f, i.getMinU(), i.getInterpolatedV(11f));
		tes.addVertexWithUV(x     , y         , z + 1f, i.getMinU(), i.getMaxV());
		tes.addVertexWithUV(x + 1f, y         , z + 1f, i.getMaxU(), i.getMaxV());
		
		
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return ID;
	}

}
