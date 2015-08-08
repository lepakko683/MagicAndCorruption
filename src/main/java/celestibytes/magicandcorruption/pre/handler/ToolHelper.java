package celestibytes.magicandcorruption.pre.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import celestibytes.magicandcorruption.pre.crafting.RecipesTools;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.IToolMod;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.McoToolMaterial;
import celestibytes.magicandcorruption.pre.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ToolHelper {
	
	public static final String TOOL_MATERIAL_HEAD = "mco_mat_head";
	public static final String TOOL_MATERIAL_HANDLE = "mco_mat_handle";
	public static final String ATTR_SPEED = "a_speed";
	public static final String ATTR_MINING_LVL = "a_minelvl";
	public static final String ATTR_DURABILITY = "a_durab";
	public static final String ATTR_ENCHANTABILITY = "a_ench";
	public static final String ATTR_HARVEST_LVL = "a_harvlvl";
	/** Base damage */
	public static final String ATTR_DAMAGE = "a_dmg";
	
	public static final String ATTR_CACHE_ICON_HEAD = "c_icon_head";
	public static final String ATTR_CACHE_ICON_HANDLE = "c_icon_handle";
	
	
	@SuppressWarnings("rawtypes")
	public static List<IToolMod> getModifiers(ItemStack tool, int filter) {
		List<IToolMod> ret = new LinkedList<IToolMod>();
		if(filter == 0) {
			filter = ~filter;
		}
		
		NBTTagCompound mods = getModsNBT(tool);
		if(mods.hasNoTags()) {
			return ret;
		}
		
		Iterator iter = mods.tagMap.keySet().iterator();
		while(iter.hasNext()) {
			String modid = (String) iter.next();
			IToolMod mod = RecipesTools.getModifier(modid);
			if(mod != null && (mod.getFlags() & filter) != 0) {
				ret.add(mod);
			}
		}
		
		return ret;
		
	}
	
	/** Called for additional modifiers, not materials */
	public static boolean canAddModifierToTool(ItemStack tool, IToolMod mod) {
		List<IToolMod> mods = getModifiers(tool, mod.getFlags() & 0xFFFFFFFE);
		if(!mods.isEmpty() && (mod.getFlags() & IToolMod.FLAG_DOMINANT) != 0) {
			return false;
		}
		
		for(IToolMod m : mods) {
			if((m.getFlags() & IToolMod.FLAG_DOMINANT) != 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void addModifierToTool(ItemStack tool, IToolMod mod) {
		NBTTagCompound nbt = getModsNBT(tool);
		if(!nbt.hasKey(mod.getModId())) {
			nbt.setTag(mod.getModId(), new NBTTagCompound());
			recalcToolAttributes(tool);
		}
	}
	
	/** Used when determining the correct repair material. */
	public static void setToolMaterial(ItemStack tool, McoToolMaterial head, McoToolMaterial handle) {
		NBTTagCompound nbt = getNBT(tool);
		nbt.setString(TOOL_MATERIAL_HEAD, head.modId);
		nbt.setString(TOOL_MATERIAL_HANDLE, handle.modId);
		addModifierToTool(tool, head);
		addModifierToTool(tool, handle);
	}
	
	public static McoToolMaterial getHeadMaterial(ItemStack tool) {
		NBTTagCompound nbt = getNBT(tool);
		IToolMod mat = RecipesTools.getModifier(nbt.getString(TOOL_MATERIAL_HEAD));
		return mat != null ? (McoToolMaterial) mat : null;
	}
	
	public static McoToolMaterial getHandleMaterial(ItemStack tool) {
		NBTTagCompound nbt = getNBT(tool);
		IToolMod mat = RecipesTools.getModifier(nbt.getString(TOOL_MATERIAL_HANDLE));
		return mat != null ? (McoToolMaterial) mat : null;
	}
	
	private static NBTTagCompound getNBT(ItemStack tool) {
		NBTTagCompound nbt = tool.getTagCompound();
		if(nbt == null) {
			nbt = new NBTTagCompound();
			tool.setTagCompound(nbt);
		}
		NBTTagCompound toolNBT = nbt.getCompoundTag("McoTool");
		if(toolNBT.hasNoTags()) {
			nbt.setTag("McoTool", toolNBT);
		}
		
		return toolNBT;
	}
	
	private static NBTTagCompound getModsNBT(ItemStack tool) {
		NBTTagCompound toolNBT = getNBT(tool);
		NBTTagCompound mods = toolNBT.getCompoundTag("Modifiers");
		if(mods.hasNoTags()) {
			toolNBT.setTag("Modifiers", mods);
		}
		
		return mods;
	}
	
	private static void recalcToolAttributes(ItemStack tool) {
		NBTTagCompound toolNBT = getNBT(tool);
		if(tool.getItem() == ModItems.pickaxe) {
			toolNBT.setInteger(ATTR_DURABILITY, calcDurability(tool));
			toolNBT.setFloat(ATTR_SPEED, calcSpeed(tool));
			toolNBT.setInteger(ATTR_ENCHANTABILITY, calcEnchantability(tool));
			McoToolMaterial mat = getHeadMaterial(tool);
			toolNBT.setInteger(ATTR_HARVEST_LVL, mat != null ? mat.getHarvestLevel() : 0);
		}
	}
		
	public static NBTTagCompound getModNBT(ItemStack tool, IToolMod mod) {
		NBTTagCompound mods = getModsNBT(tool);
		NBTTagCompound modNBT = mods.getCompoundTag(mod.getModId());
		if(modNBT.hasNoTags()) {
			mods.setTag(mod.getModId(), modNBT);
		}
		
		return modNBT;
	}
	
	public static int calcDurability(ItemStack tool) {
		List<IToolMod> flatmods = getModifiers(tool, IToolMod.FLAG_MOD_DURAB_FLAT);
		List<IToolMod> multmods = getModifiers(tool, IToolMod.FLAG_MOD_DURAB_MULT);
		
		int ret = 0;
		for(IToolMod m : flatmods) {
			ret += m.getDurabFlat(tool);
		}
		
		for(IToolMod m : multmods) {
			ret *= m.getDurabMult(tool);
		}
		
		return ret;
	}
	
	public static float calcSpeed(ItemStack tool) {
		List<IToolMod> flatmods = getModifiers(tool, IToolMod.FLAG_MOD_SPEED_FLAT);
		List<IToolMod> multmods = getModifiers(tool, IToolMod.FLAG_MOD_SPEED_MULT);
		
		float ret = 0;
		for(IToolMod m : flatmods) {
			ret += m.getSpeedFlat(tool);
		}
		
		for(IToolMod m : multmods) {
			ret *= m.getSpeedMult(tool);
		}
		
		return ret;
	}
	
	public static int calcEnchantability(ItemStack tool) {
		List<IToolMod> flatmods = getModifiers(tool, IToolMod.FLAG_MOD_ENCH_FLAT);
		List<IToolMod> multmods = getModifiers(tool, IToolMod.FLAG_MOD_ENCH_MULT);
		
		int ret = 0;
		for(IToolMod m : flatmods) {
			ret += m.getEnchFlat(tool);
		}
		
		for(IToolMod m : multmods) {
			ret *= m.getEnchMult(tool);
		}
		
		return ret;
	}
	
	public static void setToolDamage(ItemStack tool, int amount) {
		tool.setItemDamage(amount);
	}
	
	public static int getDamage(ItemStack tool) {
		return tool.getItemDamage();
	}
	
	public static int getDurabilityLeft(ItemStack tool) {
		return getDurability(tool) - getDamage(tool);
	}
	
	public static int getDurability(ItemStack tool) {
		return getNBT(tool).getInteger(ATTR_DURABILITY);
	}
	
	public static float getSpeed(ItemStack tool) {
		return getNBT(tool).getFloat(ATTR_SPEED);
	}
	
	public static int getEnchantability(ItemStack tool) {
		return getNBT(tool).getInteger(ATTR_ENCHANTABILITY);
	}
	
	public static int getHarvestLevel(ItemStack tool) {
		return getNBT(tool).getInteger(ATTR_HARVEST_LVL);
	}
	
}
