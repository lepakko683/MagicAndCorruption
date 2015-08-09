package celestibytes.magicandcorruption.pre.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import celestibytes.magicandcorruption.pre.MagicAndCorruptionPre;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.handler.ToolHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemMcoHoe extends ItemHoe implements IMcoTool {
	
	public static Map<String, IIcon> hoeHeadIcons = new HashMap<String, IIcon>();

	public ItemMcoHoe() {
		super(ToolMaterial.IRON);
		setCreativeTab(MagicAndCorruptionPre.creativeTab);
		setTextureName("iron_hoe");
		setUnlocalizedName(Ref.MOD_ID + ":mco_hoe");
	}
	
	@Override
	public void registerIcons(IIconRegister ir) {
		hoeHeadIcons.put("hoe_head_wood", ir.registerIcon(Ref.MOD_ID + ":hoe_head_wood"));
		hoeHeadIcons.put("hoe_head_stone", ir.registerIcon(Ref.MOD_ID + ":hoe_head_stone"));
		hoeHeadIcons.put("hoe_head_iron", ir.registerIcon(Ref.MOD_ID + ":hoe_head_iron"));
		hoeHeadIcons.put("hoe_head_gold", ir.registerIcon(Ref.MOD_ID + ":hoe_head_gold"));
		hoeHeadIcons.put("hoe_head_diamond", ir.registerIcon(Ref.MOD_ID + ":hoe_head_diamond"));
//		super.registerIcons(ir);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer plr, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(!plr.canPlayerEdit(x, y, z, side, stack) || ToolHelper.getDurabilityLeft(stack) == 0) {
            return false;
        } else {
            UseHoeEvent event = new UseHoeEvent(plr, stack, world, x, y, z);
            if(MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            }

            if(event.getResult() == Result.ALLOW) {
                stack.damageItem(1, plr);
                
                if(ToolHelper.getDurabilityLeft(stack) == 0) {
        			plr.renderBrokenItemStack(stack);
        		}
                
                return true;
            }

            Block block = world.getBlock(x, y, z);

            if(side != 0 && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt)) {
                Block block1 = Blocks.farmland;
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

                if(world.isRemote) {
                    return true;
                } else {
                    world.setBlock(x, y, z, block1);
                    stack.damageItem(1, plr);
                    
                    if(ToolHelper.getDurabilityLeft(stack) == 0) {
            			plr.renderBrokenItemStack(stack);
            		}
                    
                    return true;
                }
            } else {
                return false;
            }
        }
    }
	
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack rmat) {
		return ToolHelper.getHeadMaterial(stack).getRepairAmount(stack, rmat) != -1;
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.getDurability(stack);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		super.addInformation(stack, plr, info, advanced);
	}
	
	@Override
	public String getToolMaterialName() {
		return "Raritanium";
	}

	@Override
	public Map<String, IIcon> getHeadIconsMap(ItemStack tool) {
		return hoeHeadIcons;
	}
	
	@Override
	public float getAttackDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase source) {
		return 1f;
	}
}
