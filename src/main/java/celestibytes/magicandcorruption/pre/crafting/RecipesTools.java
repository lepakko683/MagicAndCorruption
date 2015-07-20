package celestibytes.magicandcorruption.pre.crafting;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import akka.util.Collections;
import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.item.ItemMcoPickaxe;
import celestibytes.magicandcorruption.pre.util.BlockPos;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class RecipesTools { // Tinker's Construct - Okkapel683 edition ;)
	
	private static Map<String, IToolMod> modLookup = new HashMap<String, IToolMod>();
	
	private static List<CraftingStack> handleMaterials = new LinkedList<CraftingStack>();
	private static List<CraftingStack> pickHeadMaterials = new LinkedList<CraftingStack>();
	
//	private static List<CraftingStack> axeHeadMaterials;
//	private static List<CraftingStack> swordHeadMaterials;
//	private static List<CraftingStack> hoeHeadMaterials;
//	private static List<CraftingStack> shovelHeadMaterials;
//	private static List<CraftingStack> sickleHeadMaterials;
	
	public static class CraftingStack {
		public final ItemStack stack;
		public final McoToolMaterial material;
		
		public CraftingStack(ItemStack stack, McoToolMaterial material) {
			this.stack = stack;
			this.material = material;
		}
		
		public boolean stackEquals(CraftingStack cs) {
			if(cs != null) {
				ItemStack o = cs.stack;
				return stack.getItem() == o.getItem() && stack.getItemDamage() == o.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, o);
			}
			
			return false;
		}
		
		public boolean stackEquals(ItemStack o) {
			if(o != null) {
				return stack.getItem() == o.getItem() && stack.getItemDamage() == o.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, o);
			}
			
			return false;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof CraftingStack) {
				return stackEquals((CraftingStack) obj);
			} else if(obj instanceof ItemStack) {
				return stackEquals((ItemStack) obj);
			}
			
			return super.equals(obj);
		}
	}
	
	public static interface IToolMod {
		
		/** Dominating mod, can't be applied to a tool if a mod of the same type already exists,
		 *  nor can any other (even non dominant) mods of the same type be applied.
		 *  Tool materials' mods are always dominant. */
		public static final int FLAG_DOMINANT       = 0x00000001;
		
		/** This mod handles drops from blocks. */
		public static final int FLAG_MOD_DROP       = 0x00000002; // ? - Auto Smelt
		
		/** This mod does extra stuff when a block is mined. */
		public static final int FLAG_MOD_MINE       = 0x00000004; // Lapis - Fortune(adds fortune enchantment, 64 lapis - I, 128 lapis - II, 256 lapis - III), Sugar / Redstone / Cookies / Cake / Speed Potion - efficiency; 16 = I / 8 x 9 = II / 64 = III /
		
		/** This mod handles repairing. */
		public static final int FLAG_MOD_REPAIR     = 0x00000008; // Blood Moss - Repair with LP, Soul-Touched Gem - repair with Soul Power/Energy, Moss - repair over time, Sparkling Moss - greater repair over time, ? - repair with vis, easy repair; repair with normal crafting (without an anvil)
		public static final int FLAG_MOD_DURAB_FLAT = 0x00000010;
		public static final int FLAG_MOD_DURAB_MULT = 0x00000020;
		public static final int FLAG_MOD_SPEED_FLAT = 0x00000040;
		public static final int FLAG_MOD_SPEED_MULT = 0x00000080;
		public static final int FLAG_MOD_ENCH_FLAT  = 0x00000100;
		public static final int FLAG_MOD_ENCH_MULT  = 0x00000200;
		
		
		/** A method to add extra info to the tool tip. */
		public void addInformation(ItemStack stack, List info);
		
		/** Returns a list of items to be dropped normally at the mined block. */
		public List<ItemStack> onBlockDrops(ItemStack stack, World world, EntityPlayer plr, List<ItemStack> drops, BlockPos block);
		
		/** Returns a list of blocks to be broken, if possible. */
		public List<BlockPos> onBlockMined(ItemStack stack, World world, EntityPlayer plr, BlockPos block);
		
		/** This gets called on every inv update for the tool to apply any repairing. */
		public void onRepairTick(ItemStack stack, EntityPlayer plr);
		
		public int getAddedDurability(ItemStack stack);
		
		public float getDurabilityMultiplier(ItemStack stack);
		
		/** Modifier id, not actual modid. */
		public String getModId();
		
	}
	
	public static abstract class McoToolMaterial implements IToolMod {
		
		public final int flags;
		public final String modId;
		
		@SideOnly(Side.CLIENT)
		public IIcon icon;
		
		@SideOnly(Side.CLIENT)
		public McoToolMaterial(String modId, int flags, IIcon icon) {
			this.modId = modId;
			this.flags = flags | FLAG_DOMINANT;
			this.icon = icon;
		}
		
		public McoToolMaterial(String modId, int flags) {
			this.modId = modId;
			this.flags = flags | FLAG_DOMINANT;
		}
		
		@Override
		@SuppressWarnings("rawtypes")
		public void addInformation(ItemStack stack, List info) {}
		
		@Override
		public void onRepairTick(ItemStack stack, EntityPlayer plr) {}
		
		@Override
		public List<BlockPos> onBlockMined(ItemStack stack, World world, EntityPlayer plr, BlockPos block) {
			return null;
		}
	}
	
	public static class McoSimpleMaterial extends McoToolMaterial {
		
		private float enchMult = 1f, durabMult = 1f, speedMult = 1f, speedFlat = 1;
		private int enchFlat = 0, durabFlat = 0;

		public McoSimpleMaterial(String modId, int flags) {
			super(modId, flags);
		}
		
		public McoSimpleMaterial setEnchMult(float val) {
			this.enchMult = val;
			return this;
		}
		
		public McoSimpleMaterial setEnchFlat(int val) {
			this.enchFlat = val;
			return this;
		}
		
		public McoSimpleMaterial setDurabMult(float val) {
			this.durabMult = val;
			return this;
		}
		
		public McoSimpleMaterial setDurabFlat(int val) {
			this.durabFlat = val;
			return this;
		}
		
		public McoSimpleMaterial setSpeedMult(float val) {
			this.speedMult = val;
			return this;
		}
		
		public McoSimpleMaterial setSpeedFlat(float val) {
			this.speedFlat = val;
			return this;
		}
		
		@Override
		public List<ItemStack> onBlockDrops(ItemStack stack, World world, EntityPlayer plr, List<ItemStack> drops, BlockPos block) {
			return drops;
		}

		@Override
		public int getAddedDurability(ItemStack stack) {
			return 0;
		}

		@Override
		public float getDurabilityMultiplier(ItemStack stack) {
			return 0;
		}

		@Override
		public String getModId() {
			return null;
		}
		
	}
	
	public static IToolMod getModifier(String id) {
		return modLookup.get(id);
	}
	
	public static boolean registerPickHeadMaterial(ItemStack mat, McoToolMaterial data) {
		if(!pickHeadMaterials.contains(mat)) {
			pickHeadMaterials.add(new CraftingStack(mat, data));
			return true;
		}
		
		return false;
	}
	
	public static boolean registerHandleMaterial(ItemStack mat, McoToolMaterial data) {
		if(!handleMaterials.contains(mat)) {
			handleMaterials.add(new CraftingStack(mat, data));
			if(!modLookup.containsKey(data.modId)) {
				modLookup.put(data.modId, data);
			}
			
			return true;
		}
		
		return false;
	}
	
	public static boolean isHeadMaterial(ItemStack stack) {
		return stack.getItem() == Items.brick;
	}
	
	public static boolean isHandleMaterial(ItemStack stack) {
		return stack.getItem() == Items.bone;
	}
	
	public static void init() {
		registerHandleMaterial(new ItemStack(Items.stick), new McoSimpleMaterial("handle_wood", IToolMod.FLAG_MOD_DURAB_MULT | IToolMod.FLAG_MOD_SPEED_MULT).setDurabMult(1.2f).setSpeedMult(0.8f));
		registerHandleMaterial(new ItemStack(Items.reeds), new McoSimpleMaterial("handle_reed", IToolMod.FLAG_MOD_DURAB_MULT | IToolMod.FLAG_MOD_SPEED_MULT).setDurabMult(1f).setSpeedMult(1f));
		
		registerPickHeadMaterial(new ItemStack(Items.flint), new McoSimpleMaterial("head_stone", IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT).setDurabFlat(64).setSpeedFlat(1f));
		registerPickHeadMaterial(new ItemStack(Items.iron_ingot), new McoSimpleMaterial("head_iron", IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT).setDurabFlat(128).setSpeedFlat(2f));
		
		RecipePickaxe rpick = new RecipePickaxe(new ItemStack(ModItems.pickaxe));
		if(rpick.valid) {
			GameRegistry.addRecipe(rpick);
		}
	}
	
	private static abstract class RecipeTool implements IRecipe {
		
		private final char[][] shape;
		private int recipeSize;
		protected final ItemStack output;
		private int recWidth, recHeight;
		
		public final boolean valid;
		
		/** shape: '#' for head, '*' for handle */
		protected RecipeTool(ItemStack output, String... shape) {
			this.output = output;
			if(shape.length >= 1 && shape.length <= 3) {
				int w = 0;
				for(int i = 0; i < shape.length; i++) {
					int l = shape[i].length();
					if(l == 0) {
						valid = false;
						this.shape = null;
						return;
					}
					
					if(w < l) {
						w = l;
					}
				}
				
				if(w > 3) {
					valid = false;
					this.shape = null;
					return;
				}
				
				recWidth = w;
				recHeight = shape.length;
				
				this.shape = new char[shape.length][w];
				for(int y = 0; y < shape.length; y++) {
					for(int x = 0; x < w; x++) {
						char c = shape[y].charAt(x);
						if(c == '#' || c == '*') {
							this.shape[y][x] = c;
							recipeSize++;
						} else {
							this.shape[y][x] = 0;
						}
					}
				}
				
				valid = true;
			} else {
				this.shape = null;
				valid = false;
			}
		}
		
		public boolean checkMatch(InventoryCrafting cinv, int xofs, int yofs, boolean mirrored) {
			ItemStack headMat = null, handleMat = null;
			
			for(int y = 0; y < 3; y++) {
				for(int x = 0; x < 3; x++) {
					int rx = x - xofs;
					int ry = y - yofs;
					
					char type = 20; // 0 = air, '#' = head, '*' = handle
					if(rx >= 0 && ry >= 0 && rx < recWidth && ry < recHeight) {
						if(mirrored) {
							type = shape[ry][recWidth - 1 - rx];
						} else {
							type = shape[ry][rx];
						}
					}
					
					ItemStack stack = cinv.getStackInRowAndColumn(rx, ry);
					
					if(stack != null) {
						if(type == 0) {
							return false;
						} else if(type == '#') {
							if(headMat != null) {
								if(!isSameMaterial(headMat, stack)) {
									return false;
								}
							} else {
								headMat = stack;
							}
						} else if(type == '*') {
							if(handleMat != null) {
								if(!isSameMaterial(handleMat, stack)) {
									return false;
								}
							} else {
								handleMat = stack;
							}
						}
					} else {
						if(type != 0) {
							return false;
						}
					}
				}
			}
			
			return areMaterialsValid(headMat, handleMat);
		}

		@Override
		public boolean matches(InventoryCrafting cinv, World world) {
			for(int x = 0; x <= 3 - recWidth; x++) {
				for(int y = 0; y <= 3 - recHeight; y++) {
					if(checkMatch(cinv, x, y, false)) {
						return true;
					}
					
					if(checkMatch(cinv, x, y, true)) {
						return true;
					}
				}
			}
			
			return false;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting cinv) {
			ItemStack headMat = getHeadStack(cinv);
			ItemStack handleMat = getHandleStack(cinv);
			
			return getResult(headMat, handleMat);
		}

		@Override
		public int getRecipeSize() {
			return recipeSize;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return output;
		}
		
		public abstract ItemStack getResult(ItemStack head, ItemStack handle);
		
		public abstract ItemStack getHeadStack(InventoryCrafting cinv);
		
		public abstract ItemStack getHandleStack(InventoryCrafting cinv);
		
		public abstract boolean areMaterialsValid(ItemStack head, ItemStack handle);
		
	}
	
	private static final class RecipePickaxe extends RecipeTool {

		protected RecipePickaxe(ItemStack output) {
			super(output, "###", " * ", " * ");
		}
		
		@Override
		public boolean areMaterialsValid(ItemStack head, ItemStack handle) {
			return listContains(pickHeadMaterials, head) && listContains(handleMaterials, handle);
		}

		@Override
		public ItemStack getHeadStack(InventoryCrafting cinv) {
			return cinv.getStackInSlot(0);
		}

		@Override
		public ItemStack getHandleStack(InventoryCrafting cinv) {
			return cinv.getStackInSlot(4);
		}

		@Override
		public ItemStack getResult(ItemStack head, ItemStack handle) {
			return output.copy();
		}

	}
	
	public static boolean listContains(List<?> list, Object obj) {
		Iterator<?> iter = list.iterator();
		while(iter.hasNext()) {
			Object e = iter.next();
			if(e.equals(obj)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isSameMaterial(ItemStack a, ItemStack b) {
		return a != null && b != null ? a.getItem() == b.getItem() && a.getItemDamage() == b.getItemDamage() && ItemStack.areItemStackTagsEqual(a, b) : false;
	}
	
}
