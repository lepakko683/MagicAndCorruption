package celestibytes.magicandcorruption.pre.client.render;

import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import celestibytes.magicandcorruption.pre.crafting.RecipesTools.McoToolMaterial;
import celestibytes.magicandcorruption.pre.handler.ToolHelper;
import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.item.IMcoTool;
import celestibytes.magicandcorruption.pre.item.ItemMcoPickaxe;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderTool implements IItemRenderer {
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(!item.hasTagCompound()) {
			return false;
		}
		
		switch(type) {
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
//			return false;
		case INVENTORY:
			return true;
		case FIRST_PERSON_MAP:
			return false;
		}
		
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		if(type == ItemRenderType.ENTITY) {
			if(helper == ItemRendererHelper.ENTITY_BOBBING) return true;
			if(helper == ItemRendererHelper.ENTITY_ROTATION) return true;
		}
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack tool, Object... data) {
		if(!(tool.getItem() instanceof IMcoTool) || tool.stackTagCompound == null) {
			return;
		}
		
		Map<String, IIcon> headIcons = ((IMcoTool)tool.getItem()).getHeadIconsMap(tool);
		McoToolMaterial headMat = ToolHelper.getHeadMaterial(tool);
		McoToolMaterial handleMat = ToolHelper.getHandleMaterial(tool);
		if(headMat == null || handleMat == null) {
			return;
		}
		
		IIcon head = headIcons.get(headMat.modId);
		IIcon handle = ItemMcoPickaxe.handleIcons.get(handleMat.modId);
		
		if(head == null || handle == null) {
			return;
		}
		
		if(type == ItemRenderType.INVENTORY) {
			renInventory(tool, head, handle, data);
			return;
		}
		
		Tessellator tes = Tessellator.instance;
		
		GL11.glPushMatrix();
		
		if(type == ItemRenderType.ENTITY) {
			GL11.glTranslatef(-0.5f, -0.25f, 0);
		}
		
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		renderItemIn2D(tes, handle.getMaxU(), handle.getMinV(), handle.getMinU(), handle.getMaxV(), handle.getIconWidth(), handle.getIconHeight(), 0.0625F);
		
		renderItemIn2D(tes, head.getMaxU(), head.getMinV(), head.getMinU(), head.getMaxV(), head.getIconWidth(), head.getIconHeight(), 0.0625F);
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glPopMatrix();
	}
	
	private void renInventory(ItemStack tool, IIcon head, IIcon handle, Object... data) {
		Tessellator tes = Tessellator.instance;
		
		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.5f);
		
		tes.startDrawingQuads();
			
			tes.setColorOpaque_I(tool.getItem().getColorFromItemStack(tool, 0));
			
			IIcon icon = handle;
			
			tes.addVertexWithUV(0,  16, 0, icon.getMinU(), icon.getMaxV());
			tes.addVertexWithUV(16, 16, 0, icon.getMaxU(), icon.getMaxV());
			tes.addVertexWithUV(16, 0,  0, icon.getMaxU(), icon.getMinV());
			tes.addVertexWithUV(0,  0,  0, icon.getMinU(), icon.getMinV());
			
			icon = head;
			
			tes.addVertexWithUV(0,  16, 0, icon.getMinU(), icon.getMaxV());
			tes.addVertexWithUV(16, 16, 0, icon.getMaxU(), icon.getMaxV());
			tes.addVertexWithUV(16, 0,  0, icon.getMaxU(), icon.getMinV());
			tes.addVertexWithUV(0,  0,  0, icon.getMinU(), icon.getMinV());
		
		tes.draw();
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
	}
	
	public static void renderItemIn2D(Tessellator tes, float xmin, float xmax, float ymin, float ymax, int iconWidth, int iconHeight, float var)
    {
        tes.startDrawingQuads();
	        tes.setNormal(0.0F, 0.0F, 1.0F);
	        tes.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)xmin, (double)ymax);
	        tes.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)ymin, (double)ymax);
	        tes.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)ymin, (double)xmax);
	        tes.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)xmin, (double)xmax);
        tes.draw();
        tes.startDrawingQuads();
	        tes.setNormal(0.0F, 0.0F, -1.0F);
	        tes.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - var), (double)xmin, (double)xmax);
	        tes.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - var), (double)ymin, (double)xmax);
	        tes.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - var), (double)ymin, (double)ymax);
	        tes.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - var), (double)xmin, (double)ymax);
        tes.draw();
        
        float f5 = 0.5F * (xmin - ymin) / (float)iconWidth;
        float f6 = 0.5F * (ymax - xmax) / (float)iconHeight;
        tes.startDrawingQuads();
	        tes.setNormal(-1.0F, 0.0F, 0.0F);
	        int k;
	        float f7;
	        float f8;
	
	        for (k = 0; k < iconWidth; ++k)
	        {
	            f7 = (float)k / (float)iconWidth;
	            f8 = xmin + (ymin - xmin) * f7 - f5;
	            tes.addVertexWithUV((double)f7, 0.0D, (double)(0.0F - var), (double)f8, (double)ymax);
	            tes.addVertexWithUV((double)f7, 0.0D, 0.0D, (double)f8, (double)ymax);
	            tes.addVertexWithUV((double)f7, 1.0D, 0.0D, (double)f8, (double)xmax);
	            tes.addVertexWithUV((double)f7, 1.0D, (double)(0.0F - var), (double)f8, (double)xmax);
	        }

        tes.draw();
        tes.startDrawingQuads();
	        tes.setNormal(1.0F, 0.0F, 0.0F);
	        float f9;
	
	        for (k = 0; k < iconWidth; ++k)
	        {
	            f7 = (float)k / (float)iconWidth;
	            f8 = xmin + (ymin - xmin) * f7 - f5;
	            f9 = f7 + 1.0F / (float)iconWidth;
	            tes.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - var), (double)f8, (double)xmax);
	            tes.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f8, (double)xmax);
	            tes.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f8, (double)ymax);
	            tes.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - var), (double)f8, (double)ymax);
	        }

        tes.draw();
        tes.startDrawingQuads();
	        tes.setNormal(0.0F, 1.0F, 0.0F);
	
	        for (k = 0; k < iconHeight; ++k)
	        {
	            f7 = (float)k / (float)iconHeight;
	            f8 = ymax + (xmax - ymax) * f7 - f6;
	            f9 = f7 + 1.0F / (float)iconHeight;
	            tes.addVertexWithUV(0.0D, (double)f9, 0.0D, (double)xmin, (double)f8);
	            tes.addVertexWithUV(1.0D, (double)f9, 0.0D, (double)ymin, (double)f8);
	            tes.addVertexWithUV(1.0D, (double)f9, (double)(0.0F - var), (double)ymin, (double)f8);
	            tes.addVertexWithUV(0.0D, (double)f9, (double)(0.0F - var), (double)xmin, (double)f8);
	        }

        tes.draw();
        tes.startDrawingQuads();
	        tes.setNormal(0.0F, -1.0F, 0.0F);
	
	        for (k = 0; k < iconHeight; ++k)
	        {
	            f7 = (float)k / (float)iconHeight;
	            f8 = ymax + (xmax - ymax) * f7 - f6;
	            tes.addVertexWithUV(1.0D, (double)f7, 0.0D, (double)ymin, (double)f8);
	            tes.addVertexWithUV(0.0D, (double)f7, 0.0D, (double)xmin, (double)f8);
	            tes.addVertexWithUV(0.0D, (double)f7, (double)(0.0F - var), (double)xmin, (double)f8);
	            tes.addVertexWithUV(1.0D, (double)f7, (double)(0.0F - var), (double)ymin, (double)f8);
	        }

        tes.draw();
    }

}
