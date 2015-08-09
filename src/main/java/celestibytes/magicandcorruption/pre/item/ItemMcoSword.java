package celestibytes.magicandcorruption.pre.item;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Multimap;

import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.handler.ToolHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMcoSword extends ItemSword implements IMcoTool {

	public static Map<String, IIcon> swordHeadIcons = new HashMap<String, IIcon>();
	
	private float baseDamage = 4.0f;
	
	public ItemMcoSword() {
		super(ToolMaterial.IRON);
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName("iron_sword");
		setUnlocalizedName(Ref.MOD_ID + ":mco_sword");
	}
	
	@Override
	public void registerIcons(IIconRegister ir) {
		swordHeadIcons.put("sword_head_wood", ir.registerIcon(Ref.MOD_ID + ":sword_head_wood"));
		swordHeadIcons.put("sword_head_stone", ir.registerIcon(Ref.MOD_ID + ":sword_head_stone"));
		swordHeadIcons.put("sword_head_iron", ir.registerIcon(Ref.MOD_ID + ":sword_head_iron"));
		swordHeadIcons.put("sword_head_gold", ir.registerIcon(Ref.MOD_ID + ":sword_head_gold"));
		swordHeadIcons.put("sword_head_diamond", ir.registerIcon(Ref.MOD_ID + ":sword_head_diamond"));
	}
	
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.block;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer plr) {
		plr.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ToolHelper.getEnchantability(stack);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.getDurability(stack);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Multimap getAttributeModifiers(ItemStack stack) {
		Multimap map = super.getItemAttributeModifiers();
		map.clear();
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon Modifier", baseDamage + ToolHelper.getAttackDamage(stack), 0));
		return map;
	}
	
	@Override
	public float getAttackDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase source) {
		return ToolHelper.getAttackDamage(stack);
	}

	@Override
	public Map<String, IIcon> getHeadIconsMap(ItemStack tool) {
		return swordHeadIcons;
	}
	
}
