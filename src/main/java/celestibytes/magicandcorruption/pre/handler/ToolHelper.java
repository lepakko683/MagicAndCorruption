package celestibytes.magicandcorruption.pre.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import celestibytes.magicandcorruption.pre.crafting.RecipesTools.IToolMod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ToolHelper {
	
	@SuppressWarnings("rawtypes")
	public static List<IToolMod> getModifiers(ItemStack tool, int filter) {
		NBTTagCompound nbt = getNBT(tool);
		if(filter == 0) {
			filter = ~filter;
		}
		
		NBTTagCompound mods = nbt.getCompoundTag("Modifiers");
		if(mods.hasNoTags()) {
			return null;
		}
		
		List<IToolMod> ret = new LinkedList<IToolMod>();
		Iterator iter = mods.tagMap.entrySet().iterator();
		while(iter.hasNext()) {
			Entry ent = (Entry) iter.next();
			String id = (String) ent.getKey();
			// TODO
		}
		
		
		
	}
	
	public static void addModifierToTool() {
		
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
		
		return nbt;
	}
	
	public static int calcMaxDurability(ItemStack tool) {
		List<IToolMod> flatmods = getModifiers(tool, IToolMod.FLAG_MOD_DURAB_FLAT);
		List<IToolMod> multmods = getModifiers(tool, IToolMod.FLAG_MOD_DURAB_MULT);
		
		int ret = 0;
		// TODO: loopz
	}
}
