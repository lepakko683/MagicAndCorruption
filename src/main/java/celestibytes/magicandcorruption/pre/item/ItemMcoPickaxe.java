package celestibytes.magicandcorruption.pre.item;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import celestibytes.magicandcorruption.pre.Ref;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ForgeHooks;

public class ItemMcoPickaxe extends ItemPickaxe {
	
	public static IIcon handle_wood = null;
	public static IIcon handle_reed = null;
	public static IIcon handle_bone = null;
	public static IIcon handle_blaze = null;
	
	public static IIcon head_stone = null;
	public static IIcon head_iron = null;
	public static IIcon head_gold = null;
	public static IIcon head_diamond = null;

	public ItemMcoPickaxe() {
		super(ToolMaterial.IRON);
		setTextureName("iron_pickaxe");
	}
	
	@Override
	public void registerIcons(IIconRegister ir) {
		handle_wood = ir.registerIcon(Ref.MOD_ID + ":handle_wood");
		handle_reed = ir.registerIcon(Ref.MOD_ID + ":handle_reed");
//		handle_bone = ir.registerIcon(Ref.MOD_ID + ":handle_bone");
//		handle_blaze = ir.registerIcon(Ref.MOD_ID + ":handle_blaze");
		
		head_stone = ir.registerIcon(Ref.MOD_ID + ":pick_head_stone");
		head_iron = ir.registerIcon(Ref.MOD_ID + ":pick_head_iron");
//		head_gold = ir.registerIcon(Ref.MOD_ID + ":pick_head_gold");
//		head_diamond = ir.registerIcon(Ref.MOD_ID + ":pick_head_diamond");
		super.registerIcons(ir);
	}
	
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return 0; // TODO
	}
	
	@Override
	public String getToolMaterialName() {
		return "Raritarium";
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack rmat) {
		//return super.getIsRepairable(stack, rmat);
		return false; // TODO
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Multimap getItemAttributeModifiers() {
		Multimap map = super.getItemAttributeModifiers();
		map.clear();
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool Modifier", 0d, 0));
		return map;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		//return super.getHarvestLevel(stack, toolClass);
		return 3; // TODO
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return super.getToolClasses(stack);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		if(ForgeHooks.isToolEffective(stack, block, meta)) {
			return 8f;
		}
		
		return 1f;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		super.addInformation(stack, plr, info, advanced);
	}

}
