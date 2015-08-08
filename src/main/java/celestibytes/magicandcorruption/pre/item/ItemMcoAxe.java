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
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMcoAxe extends ItemAxe implements IMcoTool {
	
	private static final Set someRandomBlockFromAMCClass = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});
	
	public static Map<String, IIcon> axeHeadIcons = new HashMap<String, IIcon>();

	public ItemMcoAxe() {
		super(ToolMaterial.IRON);
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName("iron_axe");
		setUnlocalizedName(Ref.MOD_ID + ":mco_axe");
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
		axeHeadIcons.put("axe_head_wood", ir.registerIcon(Ref.MOD_ID + ":axe_head_wood"));
		axeHeadIcons.put("axe_head_stone", ir.registerIcon(Ref.MOD_ID + ":axe_head_stone"));
		axeHeadIcons.put("axe_head_iron", ir.registerIcon(Ref.MOD_ID + ":axe_head_iron"));
		axeHeadIcons.put("axe_head_gold", ir.registerIcon(Ref.MOD_ID + ":axe_head_gold"));
		axeHeadIcons.put("axe_head_diamond", ir.registerIcon(Ref.MOD_ID + ":axe_head_diamond"));
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
		return "axe".equals(toolClass) ? ToolHelper.getHarvestLevel(stack) : 0;
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
		
		return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? (someRandomBlockFromAMCClass.contains(block) ? ToolHelper.getSpeed(stack) : 1f) : ToolHelper.getSpeed(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		super.addInformation(stack, plr, info, advanced);
	}
	
	@Override
	public Map<String, IIcon> getHeadIconsMap(ItemStack tool) {
		return axeHeadIcons;
	}

}
