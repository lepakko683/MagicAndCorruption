package celestibytes.magicandcorruption.pre.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.handler.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMcoShovel extends ItemSpade implements IMcoTool {
	
	private static final Set someRandomBlockFromAMCClass = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	
	public static Map<String, IIcon> shovelHeadIcons = new HashMap<String, IIcon>();

	public ItemMcoShovel() {
		super(ToolMaterial.IRON);
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName("iron_shovel");
		setUnlocalizedName(Ref.MOD_ID + ":mco_shovel");
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase ent) {
		if(ToolHelper.getDurabilityLeft(stack) == 0) {
			return false;
		}
		
		if(block.getBlockHardness(world, x, y, z) != 0d) {
			stack.damageItem(1, ent);
		}
		
		if(ToolHelper.getDurabilityLeft(stack) == 0) {
			ent.renderBrokenItemStack(stack);
		}
		
		return true;
	}
	
	@Override
	public void registerIcons(IIconRegister ir) {
		shovelHeadIcons.put("shovel_head_wood", ir.registerIcon(Ref.MOD_ID + ":shovel_head_wood"));
		shovelHeadIcons.put("shovel_head_stone", ir.registerIcon(Ref.MOD_ID + ":shovel_head_stone"));
		shovelHeadIcons.put("shovel_head_iron", ir.registerIcon(Ref.MOD_ID + ":shovel_head_iron"));
		shovelHeadIcons.put("shovel_head_gold", ir.registerIcon(Ref.MOD_ID + ":shovel_head_gold"));
		shovelHeadIcons.put("shovel_head_diamond", ir.registerIcon(Ref.MOD_ID + ":shovel_head_diamond"));
		super.registerIcons(ir);
	}
	
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ToolHelper.getEnchantability(stack);
	}
	
	@Override
	public String getToolMaterialName() {
		return "Raritanium";
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack rmat) {
		return ToolHelper.getHeadMaterial(stack).getRepairAmount(stack, rmat) != -1;
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.getDurability(stack);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return "shovel".equals(toolClass) ? ToolHelper.getHarvestLevel(stack) : 0;
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return super.getToolClasses(stack);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		if(ToolHelper.getDurabilityLeft(stack) == 0) {
			return 0.1f;
		}
		
		String tcls = block.getHarvestTool(meta);
		for(String type : stack.getItem().getToolClasses(stack)) {
			if(block.isToolEffective(type, meta)) {
				return ToolHelper.getSpeed(stack);
			} else if(tcls != null && tcls.equals(type)) {
				return ToolHelper.getSpeed(stack);
			}
		}
		
		return !func_150897_b(block) ? (someRandomBlockFromAMCClass.contains(block) ? ToolHelper.getSpeed(stack) : 1f) : ToolHelper.getSpeed(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		super.addInformation(stack, plr, info, advanced);
	}
	
	@Override
	public Map<String, IIcon> getHeadIconsMap(ItemStack tool) {
		return shovelHeadIcons;
	}

}
