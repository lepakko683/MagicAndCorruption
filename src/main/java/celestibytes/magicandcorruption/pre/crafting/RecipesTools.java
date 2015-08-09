package celestibytes.magicandcorruption.pre.crafting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import celestibytes.magicandcorruption.pre.handler.ToolHelper;
import celestibytes.magicandcorruption.pre.init.ModItems;
import celestibytes.magicandcorruption.pre.init.ToolInit;
import celestibytes.magicandcorruption.pre.item.IMcoTool;
import celestibytes.magicandcorruption.pre.util.BlockPos;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;

public class RecipesTools { // Tinker's Tools - Okkapel683 edition ;)
	
	private static Map<String, IToolMod> modLookup = new HashMap<String, IToolMod>();
	
	private static List<CraftingStack> handleMaterials = new LinkedList<CraftingStack>();
	
	private static List<CraftingStack> swordHeadMaterials = new LinkedList<CraftingStack>();
	private static List<CraftingStack> pickHeadMaterials = new LinkedList<CraftingStack>();
	private static List<CraftingStack> axeHeadMaterials = new LinkedList<CraftingStack>();
	private static List<CraftingStack> hoeHeadMaterials = new LinkedList<CraftingStack>();
	private static List<CraftingStack> shovelHeadMaterials = new LinkedList<CraftingStack>();
	private static List<CraftingStack> scytheHeadMaterials = new LinkedList<CraftingStack>();
	
	public static void init() {
		ToolInit.init();
		
		RecipeSword rsword = new RecipeSword(new ItemStack(ModItems.sword));
		if(rsword.valid) {
			RecipeSorter.register("mco_recipe_sword", rsword.getClass(), RecipeSorter.Category.SHAPED, "");
			GameRegistry.addRecipe(rsword);
		}
		
		RecipePickaxe rpick = new RecipePickaxe(new ItemStack(ModItems.pickaxe));
		if(rpick.valid) {
			RecipeSorter.register("mco_recipe_pickaxe", rpick.getClass(), RecipeSorter.Category.SHAPED, "");
			GameRegistry.addRecipe(rpick);
		}
		
		RecipeAxe raxe = new RecipeAxe(new ItemStack(ModItems.axe));
		if(raxe.valid) {
			RecipeSorter.register("mco_recipe_axe", raxe.getClass(), RecipeSorter.Category.SHAPED, "");
			GameRegistry.addRecipe(raxe);
		}
		
		RecipeShovel rshovel = new RecipeShovel(new ItemStack(ModItems.shovel));
		if(rshovel.valid) {
			RecipeSorter.register("mco_recipe_shovel", rshovel.getClass(), RecipeSorter.Category.SHAPED, "");
			GameRegistry.addRecipe(rshovel);
		}
		
		RecipeHoe rhoe = new RecipeHoe(new ItemStack(ModItems.hoe));
		if(rhoe.valid) {
			RecipeSorter.register("mco_recipe_hoe", rhoe.getClass(), RecipeSorter.Category.SHAPED, "");
			GameRegistry.addRecipe(rhoe);
		}
		
		RecipeSorter.register("mco_recipes_repair", RecipesRepair.class, RecipeSorter.Category.SHAPELESS, "");
		GameRegistry.addRecipe(new RecipesRepair());
	}
	
	public static List<CraftingStack> getHandleMaterials() {
		return handleMaterials;
	}
	
	public static List<CraftingStack> getSwordHeadMaterials() {
		return swordHeadMaterials;
	}
	
	public static List<CraftingStack> getPickHeadMaterials() {
		return pickHeadMaterials;
	}
	
	public static List<CraftingStack> getAxeHeadMaterials() {
		return axeHeadMaterials;
	}
	
	public static List<CraftingStack> getHoeHeadMaterials() {
		return hoeHeadMaterials;
	}
	
	public static List<CraftingStack> getShovelHeadMaterials() {
		return shovelHeadMaterials;
	}
	
	public static List<CraftingStack> getScytheHeadMaterials() {
		return scytheHeadMaterials;
	}
	
	private static boolean registerToolMaterial(List<CraftingStack> list, ItemStack mat, McoToolMaterial data) {
		if(!listContains(list, mat)) {
			list.add(new CraftingStack(mat, data));
			if(!modLookup.containsKey(data.modId)) {
				modLookup.put(data.modId, data);
			}
			
			return true;
		}
		
		return false;
	}
	
	public static boolean registerHandleMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(handleMaterials, mat, data);
	}
	
	public static boolean registerSwordHeadMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(swordHeadMaterials, mat, data);
	}
	
	public static boolean registerPickHeadMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(pickHeadMaterials, mat, data);
	}
	
	public static boolean registerAxeHeadMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(axeHeadMaterials, mat, data);
	}
	
	public static boolean registerHoeHeadMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(hoeHeadMaterials, mat, data);
	}
	
	public static boolean registerShovelHeadMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(shovelHeadMaterials, mat, data);
	}
	
	public static boolean registerScytheHeadMaterial(ItemStack mat, McoToolMaterial data) {
		return registerToolMaterial(scytheHeadMaterials, mat, data);
	}
	
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
	
	public static CraftingStack getCSForMaterial(McoToolMaterial mat, List<CraftingStack> css) {
		for(CraftingStack cs : css) {
			if(cs.material.equals(mat)) {
				return cs;
			}
		}
		
		return null;
	}
	
	public static interface IToolMod {
		
		/** Dominating mod, can't be applied to a tool if a mod of the same type already exists,
		 *  nor can any other (even non dominant) mods of the same type be applied.
		 */
		public static final int FLAG_DOMINANT       = 0x00000001;
		
		/** This mod handles drops from blocks. */
		public static final int FLAG_MOD_DROP       = 0x00000002; // ? - Auto Smelt
		
		/** This mod does extra stuff when a block is mined. */
		public static final int FLAG_MOD_MINE       = 0x00000004; // Lapis - Fortune(adds fortune enchantment, 64 lapis - I, 192 lapis - II, 448 lapis - III), Sugar / Redstone / Cookies / Cake / Speed Potion - efficiency; 16 = I / 8 x 9 = II / 64 = III / ? / ?
		
		/** This mod handles repairing. */
		public static final int FLAG_MOD_REPAIR     = 0x00000008; // Blood Moss - Repair with LP, Soul-Touched Gem - repair with Soul Power/Energy, Moss - repair over time, Sparkling Moss - greater repair over time, ? - repair with vis, easy repair; repair with normal crafting (without an anvil)
		public static final int FLAG_MOD_DURAB_FLAT = 0x00000010;
		public static final int FLAG_MOD_DURAB_MULT = 0x00000020;
		public static final int FLAG_MOD_SPEED_FLAT = 0x00000040;
		public static final int FLAG_MOD_SPEED_MULT = 0x00000080;
		public static final int FLAG_MOD_ENCH_FLAT  = 0x00000100;
		public static final int FLAG_MOD_ENCH_MULT  = 0x00000200;
		public static final int FLAG_MOD_DMG_FLAT   = 0x00000400;
		public static final int FLAG_MOD_DMG_MULT   = 0x00000800;
		
		
		/** A method to add extra info to the tool tip. */
		@SuppressWarnings("rawtypes")
		public void addInformation(ItemStack stack, List info);
		
		/** Returns a list of items to be dropped normally at the mined block. */
		public List<ItemStack> onBlockDrops(ItemStack stack, World world, EntityPlayer plr, List<ItemStack> drops, BlockPos block);
		
		/** Returns a list of additional blocks to be broken. */
		public List<BlockPos> onBlockMined(ItemStack stack, World world, EntityPlayer plr, BlockPos block);
		
		/** This gets called on every inv update for the tool to apply any repairing. */
		public void onRepairTick(ItemStack stack, EntityPlayer plr);
		
		public int getDurabFlat(ItemStack stack);
		public float getDurabMult(ItemStack stack);
		
		public float getSpeedFlat(ItemStack stack);
		public float getSpeedMult(ItemStack stack);
		
		public int getEnchFlat(ItemStack stack);
		public float getEnchMult(ItemStack stack);
		
		public float getAttackDmgFlat(ItemStack stack);
		public float getAttackDmgMult(ItemStack stack);
		
		/** Modifier id, not actual modid. */
		public String getModId();
		
		public int getFlags();
		
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
		
		/** return -1 if not a valid repair material */
		public abstract int getRepairAmount(ItemStack tool, ItemStack repairStack);
		
		public abstract boolean isPrimaryRepairMaterial(ItemStack repairStack);
		
		@Override
		public String getModId() {
			return modId;
		}
		
		@Override
		public int getFlags() {
			return flags;
		}
		
		public int getHarvestLevel() {
			return 0;
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
		
		private static class RepairStack {
			public final ItemStack stack;
			public final int amount;
			public final float amountf;
			
			public RepairStack(ItemStack stack, int amount) {
				this.stack = stack;
				this.amount = amount;
				this.amountf = 0f;
			}
			
			public RepairStack(ItemStack stack, float amount) {
				this.stack = stack;
				this.amount = -1;
				this.amountf = amount;
			}
			
			public int getRepairAmount(ItemStack tool) {
				if(amount == -1) {
					return (int) Math.ceil(((float)ToolHelper.getDurability(tool)) * amountf);
				}
				
				return amount;
			}
			
			public boolean equals(ItemStack repairStack) {
				return isSameMaterial(stack, repairStack);
			}
		}
		
		private final List<RepairStack> repairStacks = new LinkedList<RepairStack>();
		
		private float enchMult = 1f, durabMult = 1f, speedMult = 1f, speedFlat = 1f, attackDmgMult = 1f, attackDmgFlat = 0f;
		private int enchFlat = 0, durabFlat = 0;
		private int harvestLevel = 0;

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
		
		public McoSimpleMaterial setHarvestLevel(int val) {
			this.harvestLevel = val;
			return this;
		}
		
		public McoSimpleMaterial setAttackDmgFlat(float val) {
			this.attackDmgFlat = val;
			return this;
		}
		
		public McoSimpleMaterial setAttackDmgMult(float val) {
			this.attackDmgMult = val;
			return this;
		}
		
		public McoSimpleMaterial addRepairStack(ItemStack repairStack, int repairAmount) {
			repairStacks.add(new RepairStack(repairStack, repairAmount));
			return this;
		}
		
		public McoSimpleMaterial addRepairStack(ItemStack repairStack, float repairAmount) {
			repairStacks.add(new RepairStack(repairStack, repairAmount));
			return this;
		}
		
		@Override
		public List<ItemStack> onBlockDrops(ItemStack stack, World world, EntityPlayer plr, List<ItemStack> drops, BlockPos block) {
			return drops;
		}

		@Override
		public int getDurabFlat(ItemStack stack) {
			return durabFlat;
		}

		@Override
		public float getDurabMult(ItemStack stack) {
			return durabMult;
		}

		@Override
		public float getSpeedFlat(ItemStack stack) {
			return speedFlat;
		}

		@Override
		public float getSpeedMult(ItemStack stack) {
			return speedMult;
		}

		@Override
		public int getEnchFlat(ItemStack stack) {
			return enchFlat;
		}

		@Override
		public float getEnchMult(ItemStack stack) {
			return enchMult;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		public float getAttackDmgFlat(ItemStack stack) {
			return attackDmgFlat;
		}
		
		public float getAttackDmgMult(ItemStack stack) {
			return attackDmgMult;
		}
		
		@Override
		public int getRepairAmount(ItemStack tool, ItemStack repairStack) {
			for(RepairStack rs : repairStacks) {
				if(rs.equals(repairStack)) {
					return rs.getRepairAmount(tool);
				} else {
					System.out.println("not equal: " + tool.getItem() + " : " + repairStack.getItem());
				}
			}
			return -1;
		}
		
		@Override
		public boolean isPrimaryRepairMaterial(ItemStack repairStack) {
			return repairStacks.size() >= 1 ? repairStacks.get(0).equals(repairStack) : false;
		}

	}
	
	public static IToolMod getModifier(String id) {
		return modLookup.get(id);
	}
	
	public static McoToolMaterial getMaterial(String id) {
		IToolMod mod = modLookup.get(id);
		if(mod instanceof McoToolMaterial) {
			return (McoToolMaterial) mod;
		}
		
		return null;
	}
	
	public static boolean isHeadMaterial(ItemStack stack) {
		return stack.getItem() == Items.brick;
	}
	
	public static boolean isHandleMaterial(ItemStack stack) {
		return stack.getItem() == Items.bone;
	}
	
	private static abstract class RecipeTool implements IRecipe {
		
		protected boolean mirrored = false;
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
				
				this.shape = new char[recHeight][recWidth];
				for(int y = 0; y < recHeight; y++) {
					for(int x = 0; x < recWidth; x++) {
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
//			System.out.println(">checkMatch");
			for(int y = 0; y < 3; y++) {
//				System.out.print("\t|");
				for(int x = 0; x < 3; x++) {
					int rx = x - xofs;
					int ry = y - yofs;
					
					char type = 0; // 0 = air, '#' = head, '*' = handle
					if(rx >= 0 && ry >= 0 && rx < recWidth && ry < recHeight) {
						if(mirrored) {
							type = shape[ry][recWidth - 1 - rx];
						} else {
							type = shape[ry][rx];
						}
					}
					
//					System.out.print(type == 0 ? "X" : type == 20 ? "_" : type);
					
					ItemStack stack = cinv.getStackInRowAndColumn(x, y);
//					System.out.println((mirrored ? "[-]" : "[+]") + " r: " + rx + " " + ry + ", xy: " + x + " " + y + ", ofs: " + xofs + " " + yofs + " type: " + (type == 0 ? "X" : type == 20 ? "U" : type) + ", got: " + (stack == null ? "O" : "X"));
					
					if(type != 0) {
						if(stack == null) {
//							System.out.print("E\n");
							return false;
						}
						if(type == '#') {
							if(headMat != null) {
								if(!isSameMaterial(headMat, stack)) {
//									System.out.print("E\n");
									return false;
								}
							} else {
								headMat = stack;
							}
						} else if(type == '*') {
							if(handleMat != null) {
								if(!isSameMaterial(handleMat, stack)) {
//									System.out.print("E\n");
									return false;
								}
							} else {
								handleMat = stack;
							}
						}
					} else {
						if(stack != null) {
//							System.out.print("E\n");
							return false;
						}
					}
				}
//				System.out.print("|\n");
			}
			
			return areMaterialsValid(headMat, handleMat);
		}

		@Override
		public boolean matches(InventoryCrafting cinv, World world) {
//			System.out.println(">matches");
//			for(int y = 0; y < recHeight; y++) {
//				System.out.print("\t|");
//				for(int x = 0; x < recWidth; x++) {
//					char c = this.shape[y][x];
//					System.out.print(c == 0 ? "X" : c);
//				}
//				System.out.print("|\n");
//			}
			
			
			for(int x = 0; x <= 3 - recWidth; x++) {
				for(int y = 0; y <= 3 - recHeight; y++) {
					if(checkMatch(cinv, x, y, true)) {
						this.mirrored = false;
						return true;
					}
					
					if(checkMatch(cinv, x, y, false)) {
						this.mirrored = true;
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
			ItemStack ret = output.copy();
			CraftingStack headMat = getCStack(head, pickHeadMaterials), handleMat = getCStack(handle, handleMaterials);
			if(headMat != null && handleMat != null) {
				ToolHelper.setToolMaterial(ret, headMat.material, handleMat.material);
//				ToolHelper.addModifierToTool(ret, headMat.material);
//				ToolHelper.addModifierToTool(ret, handleMat.material);
			} else {
				System.out.println("one of the materials is null");
				return null;
			}
			
			return ret;
		}
	}
	
	private static final class RecipeAxe extends RecipeTool {

		protected RecipeAxe(ItemStack output) {
			super(output, "##", "#*", " *");
		}
		
		@Override
		public boolean areMaterialsValid(ItemStack head, ItemStack handle) {
			return listContains(axeHeadMaterials, head) && listContains(handleMaterials, handle);
		}

		@Override
		public ItemStack getHeadStack(InventoryCrafting cinv) {
			for(int i = 0; i < 3; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getHandleStack(InventoryCrafting cinv) {
			for(int i = 6; i < 9; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getResult(ItemStack head, ItemStack handle) {
			ItemStack ret = output.copy();
			CraftingStack headMat = getCStack(head, axeHeadMaterials), handleMat = getCStack(handle, handleMaterials);
			if(headMat != null && handleMat != null) {
				ToolHelper.setToolMaterial(ret, headMat.material, handleMat.material);
			} else {
				System.out.println("one of the materials is null");
				return null;
			}
			
			return ret;
		}
	}
	
	private static final class RecipeShovel extends RecipeTool {

		protected RecipeShovel(ItemStack output) {
			super(output, "#", "*", "*");
		}
		
		@Override
		public boolean areMaterialsValid(ItemStack head, ItemStack handle) {
			return listContains(shovelHeadMaterials, head) && listContains(handleMaterials, handle);
		}

		@Override
		public ItemStack getHeadStack(InventoryCrafting cinv) {
			for(int i = 0; i < 3; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getHandleStack(InventoryCrafting cinv) {
			for(int i = 3; i < 6; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getResult(ItemStack head, ItemStack handle) {
			ItemStack ret = output.copy();
			CraftingStack headMat = getCStack(head, shovelHeadMaterials), handleMat = getCStack(handle, handleMaterials);
			if(headMat != null && handleMat != null) {
				ToolHelper.setToolMaterial(ret, headMat.material, handleMat.material);
			} else {
				System.out.println("one of the materials is null");
				return null;
			}
			
			return ret;
		}
	}
	
	private static final class RecipeHoe extends RecipeTool {

		protected RecipeHoe(ItemStack output) {
			super(output, "##", " *", " *");
		}
		
		@Override
		public boolean areMaterialsValid(ItemStack head, ItemStack handle) {
			return listContains(hoeHeadMaterials, head) && listContains(handleMaterials, handle);
		}

		@Override
		public ItemStack getHeadStack(InventoryCrafting cinv) {
			for(int i = 0; i < 3; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getHandleStack(InventoryCrafting cinv) {
			for(int i = 3; i < 6; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getResult(ItemStack head, ItemStack handle) {
			ItemStack ret = output.copy();
			CraftingStack headMat = getCStack(head, hoeHeadMaterials), handleMat = getCStack(handle, handleMaterials);
			if(headMat != null && handleMat != null) {
				ToolHelper.setToolMaterial(ret, headMat.material, handleMat.material);
			} else {
				System.out.println("one of the materials is null");
				return null;
			}
			
			return ret;
		}
	}
	
	private static final class RecipeSword extends RecipeTool {

		protected RecipeSword(ItemStack output) {
			super(output, "#", "#", "*");
		}
		
		@Override
		public boolean areMaterialsValid(ItemStack head, ItemStack handle) {
			return listContains(swordHeadMaterials, head) && listContains(handleMaterials, handle);
		}

		@Override
		public ItemStack getHeadStack(InventoryCrafting cinv) {
			for(int i = 0; i < 3; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getHandleStack(InventoryCrafting cinv) {
			for(int i = 6; i < 9; i++) {
				ItemStack is = cinv.getStackInSlot(i);
				if(is != null) {
					return is;
				}
			}
			return null;
		}

		@Override
		public ItemStack getResult(ItemStack head, ItemStack handle) {
			ItemStack ret = output.copy();
			CraftingStack headMat = getCStack(head, swordHeadMaterials), handleMat = getCStack(handle, handleMaterials);
			if(headMat != null && handleMat != null) {
				ToolHelper.setToolMaterial(ret, headMat.material, handleMat.material);
			} else {
				System.out.println("one of the materials is null");
				return null;
			}
			
			return ret;
		}
	}
	
	public static class RecipesRepair implements IRecipe {
		
		private ItemStack tool;
		private int toolSlot = 0;

		@Override
		public boolean matches(InventoryCrafting cinv, World world) {
			ItemStack tool = null;
			
			for(int i = 0; i < cinv.getSizeInventory(); i++) {
				ItemStack st = cinv.getStackInSlot(i);
				if(st != null) {
					if(st.getItem() instanceof IMcoTool) {
						if(tool != null) {
							return false;
						}
						
						if(ToolHelper.getDamage(st) == 0) {
							return false;
						}
						
						toolSlot = i;
						tool = st;
					}
				}
			}
			
			if(tool == null) {
				return false;
			}
			
			McoToolMaterial mat = ToolHelper.getHeadMaterial(tool);
			if(mat == null) {
				return false;
			}
			
			boolean toolOnly = true;
			
			for(int i = 0; i < cinv.getSizeInventory(); i++) {
				ItemStack st = cinv.getStackInSlot(i);
				if(st != null && i != toolSlot) {
					toolOnly = false;
					if(mat.getRepairAmount(tool, st) == -1) {
						return false;
					}
				}
			}
			
			this.tool = tool;
			return !toolOnly;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting cinv) {
			ItemStack tool = this.tool.copy();
			McoToolMaterial mat = ToolHelper.getHeadMaterial(tool);
			
			boolean cutLoop = false;
			int toRepair = ToolHelper.getDamage(tool);
			int repairAmount = 0;
			
			for(int i = 0; i < cinv.getSizeInventory(); i++) {
				ItemStack st = cinv.getStackInSlot(i);
				if(st != null && i != toolSlot) {
					if(cutLoop) {
						return null;
					}
					repairAmount += mat.getRepairAmount(tool, st);
					if(repairAmount >= toRepair) {
						repairAmount = toRepair;
						cutLoop = true;
					}
				}
			}
			
			ToolHelper.setToolDamage(tool, toRepair - repairAmount);
			
			return tool;
		}

		@Override
		public int getRecipeSize() {
			return 10;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return null;
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
	
	public static CraftingStack getCStack(ItemStack key, List<CraftingStack> list) {
		for(CraftingStack cs : list) {
			if(cs.equals(key)) {
				return cs;
			}
		}
		
		return null;
	}
	
	private static boolean isSameMaterial(ItemStack a, ItemStack b) {
		return a != null && b != null ? a.getItem() == b.getItem() && a.getItemDamage() == b.getItemDamage() && ItemStack.areItemStackTagsEqual(a, b) : false;
	}
	
}
