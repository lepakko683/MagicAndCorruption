package celestibytes.magicandcorruption.pre.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.CraftingStack;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.McoToolMaterial;
import celestibytes.magicandcorruption.pre.handler.ToolHelper;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemMcoPickaxe extends ItemPickaxe implements IMcoTool {
	
	private static final Set someRandomBlockFromAMCClass = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	public static Map<String, IIcon> pickHeadIcons = new HashMap<String, IIcon>();
	public static Map<String, IIcon> handleIcons = new HashMap<String, IIcon>();
	
	public ItemMcoPickaxe() {
		super(ToolMaterial.IRON);
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName("iron_pickaxe");
		setUnlocalizedName(Ref.MOD_ID + ":mco_pickaxe");
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
		handleIcons.put("handle_wood", ir.registerIcon(Ref.MOD_ID + ":handle_wood"));
		handleIcons.put("handle_reed", ir.registerIcon(Ref.MOD_ID + ":handle_reed"));
		handleIcons.put("handle_bone", ir.registerIcon(Ref.MOD_ID + ":handle_bone"));
		handleIcons.put("handle_blaze", ir.registerIcon(Ref.MOD_ID + ":handle_blaze"));

		pickHeadIcons.put("pick_head_wood", ir.registerIcon(Ref.MOD_ID + ":pick_head_wood"));
		pickHeadIcons.put("pick_head_stone", ir.registerIcon(Ref.MOD_ID + ":pick_head_stone"));
		pickHeadIcons.put("pick_head_iron", ir.registerIcon(Ref.MOD_ID + ":pick_head_iron"));
		pickHeadIcons.put("pick_head_gold", ir.registerIcon(Ref.MOD_ID + ":pick_head_gold"));
		pickHeadIcons.put("pick_head_diamond", ir.registerIcon(Ref.MOD_ID + ":pick_head_diamond"));
//		super.registerIcons(ir);
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item item, CreativeTabs ctab, List list) {
		McoToolMaterial handle = RecipesTools.getMaterial("handle_wood");
		if(handle == null) {
			return;
		}
		
		for(CraftingStack cs : RecipesTools.getPickHeadMaterials()) {
			ItemStack is = new ItemStack(this);
			
			ToolHelper.setToolMaterial(is, cs.material, handle);
			list.add(is);
		}
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
		return "pickaxe".equals(toolClass) ? ToolHelper.getHarvestLevel(stack) : 0;
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
		
		return block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock ? (someRandomBlockFromAMCClass.contains(block) ? ToolHelper.getSpeed(stack) : 1f) : ToolHelper.getSpeed(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		super.addInformation(stack, plr, info, advanced);
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Multimap getAttributeModifiers(ItemStack stack) {
		Multimap map = super.getItemAttributeModifiers();
		map.clear();
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", 2f + ToolHelper.getAttackDamage(stack), 0));
		return map;
	}

	@Override
	public Map<String, IIcon> getHeadIconsMap(ItemStack tool) {
		return pickHeadIcons;
	}
	
	@Override
	public float getAttackDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase source) {
		return 1f;
	}

}
