package celestibytes.magicandcorruption.pre.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.IToolMod;
import celestibytes.magicandcorruption.pre.crafting.RecipesTools.McoSimpleMaterial;

public class ToolInit {
	
	public static void init() {
		addHandles();
		addSwordHeads();
		addPickHeads();
		addAxeHeads();
		addShovelHeads();
		addHoeHeads();
	}
	
	private static void addHandles() {
		RecipesTools.registerHandleMaterial(new ItemStack(Items.stick), new McoSimpleMaterial("handle_wood", 
				IToolMod.FLAG_MOD_DURAB_MULT | IToolMod.FLAG_MOD_SPEED_MULT)
				.setDurabMult(1.2f).setSpeedMult(0.8f));
		
		RecipesTools.registerHandleMaterial(new ItemStack(Items.reeds), new McoSimpleMaterial("handle_reed",
				IToolMod.FLAG_MOD_DURAB_MULT | IToolMod.FLAG_MOD_SPEED_MULT)
				.setDurabMult(1f).setSpeedMult(1f));
		
		RecipesTools.registerHandleMaterial(new ItemStack(Items.bone), new McoSimpleMaterial("handle_bone",
				IToolMod.FLAG_MOD_DURAB_MULT | IToolMod.FLAG_MOD_SPEED_MULT)
				.setDurabMult(1.25f).setSpeedMult(1.25f));
		
		RecipesTools.registerHandleMaterial(new ItemStack(Items.blaze_rod), new McoSimpleMaterial("handle_blaze",
				IToolMod.FLAG_MOD_DURAB_MULT | IToolMod.FLAG_MOD_SPEED_MULT)
				.setDurabMult(0.75f).setSpeedMult(2f));
	}
	
	private static void addSwordHeads() {
		RecipesTools.registerSwordHeadMaterial(new ItemStack(Blocks.planks), new McoSimpleMaterial("sword_head_wood",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(59).setSpeedFlat(2f).setHarvestLevel(0).setEnchFlat(15).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Blocks.planks), 0.25f));
		
		RecipesTools.registerSwordHeadMaterial(new ItemStack(Items.flint), new McoSimpleMaterial("sword_head_stone",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(131).setSpeedFlat(4f).setHarvestLevel(1).setEnchFlat(5).setAttackDmgFlat(1f).addRepairStack(new ItemStack(Items.flint), 0.25f));
		
		RecipesTools.registerSwordHeadMaterial(new ItemStack(Items.iron_ingot), new McoSimpleMaterial("sword_head_iron",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(250).setSpeedFlat(6f).setHarvestLevel(2).setEnchFlat(14).setAttackDmgFlat(2f).addRepairStack(new ItemStack(Items.iron_ingot), 0.25f));
		
		RecipesTools.registerSwordHeadMaterial(new ItemStack(Items.gold_ingot), new McoSimpleMaterial("sword_head_gold",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(32).setSpeedFlat(12f).setHarvestLevel(0).setEnchFlat(22).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Items.gold_ingot), 0.25f));
		
		RecipesTools.registerSwordHeadMaterial(new ItemStack(Items.diamond), new McoSimpleMaterial("sword_head_diamond",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(1561).setSpeedFlat(8f).setHarvestLevel(3).setEnchFlat(10).setAttackDmgFlat(3f).addRepairStack(new ItemStack(Items.diamond), 0.25f));
	}
	
	private static void addPickHeads() {
		RecipesTools.registerPickHeadMaterial(new ItemStack(Blocks.planks), new McoSimpleMaterial("pick_head_wood",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(59).setSpeedFlat(2f).setHarvestLevel(0).setEnchFlat(15).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Blocks.planks), 0.25f));
		
		RecipesTools.registerPickHeadMaterial(new ItemStack(Items.flint), new McoSimpleMaterial("pick_head_stone",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(131).setSpeedFlat(4f).setHarvestLevel(1).setEnchFlat(5).setAttackDmgFlat(1f).addRepairStack(new ItemStack(Items.flint), 0.25f));
		
		RecipesTools.registerPickHeadMaterial(new ItemStack(Items.iron_ingot), new McoSimpleMaterial("pick_head_iron",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(250).setSpeedFlat(6f).setHarvestLevel(2).setEnchFlat(14).setAttackDmgFlat(2f).addRepairStack(new ItemStack(Items.iron_ingot), 0.25f));
		
		RecipesTools.registerPickHeadMaterial(new ItemStack(Items.gold_ingot), new McoSimpleMaterial("pick_head_gold",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(32).setSpeedFlat(12f).setHarvestLevel(0).setEnchFlat(22).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Items.gold_ingot), 0.25f));
		
		RecipesTools.registerPickHeadMaterial(new ItemStack(Items.diamond), new McoSimpleMaterial("pick_head_diamond",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(1561).setSpeedFlat(8f).setHarvestLevel(3).setEnchFlat(10).setAttackDmgFlat(3f).addRepairStack(new ItemStack(Items.diamond), 0.25f));
	}
	
	private static void addAxeHeads() {
		RecipesTools.registerAxeHeadMaterial(new ItemStack(Blocks.planks), new McoSimpleMaterial("axe_head_wood",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(59).setSpeedFlat(2f).setHarvestLevel(0).setEnchFlat(15).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Blocks.planks), 0.25f));
		
		RecipesTools.registerAxeHeadMaterial(new ItemStack(Items.flint), new McoSimpleMaterial("axe_head_stone",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(131).setSpeedFlat(4f).setHarvestLevel(1).setEnchFlat(5).setAttackDmgFlat(1f).addRepairStack(new ItemStack(Items.flint), 0.25f));
		
		RecipesTools.registerAxeHeadMaterial(new ItemStack(Items.iron_ingot), new McoSimpleMaterial("axe_head_iron",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(250).setSpeedFlat(6f).setHarvestLevel(2).setEnchFlat(14).setAttackDmgFlat(2f).addRepairStack(new ItemStack(Items.iron_ingot), 0.25f));
		
		RecipesTools.registerAxeHeadMaterial(new ItemStack(Items.gold_ingot), new McoSimpleMaterial("axe_head_gold",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(32).setSpeedFlat(12f).setHarvestLevel(0).setEnchFlat(22).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Items.gold_ingot), 0.25f));
		
		RecipesTools.registerAxeHeadMaterial(new ItemStack(Items.diamond), new McoSimpleMaterial("axe_head_diamond",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(1561).setSpeedFlat(8f).setHarvestLevel(3).setEnchFlat(10).setAttackDmgFlat(3f).addRepairStack(new ItemStack(Items.diamond), 0.25f));
	}
	
	private static void addShovelHeads() {
		RecipesTools.registerShovelHeadMaterial(new ItemStack(Blocks.planks), new McoSimpleMaterial("shovel_head_wood",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(59).setSpeedFlat(2f).setHarvestLevel(0).setEnchFlat(15).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Blocks.planks), 0.25f));
		
		RecipesTools.registerShovelHeadMaterial(new ItemStack(Items.flint), new McoSimpleMaterial("shovel_head_stone",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(131).setSpeedFlat(4f).setHarvestLevel(1).setEnchFlat(5).setAttackDmgFlat(1f).addRepairStack(new ItemStack(Items.flint), 0.25f));
		
		RecipesTools.registerShovelHeadMaterial(new ItemStack(Items.iron_ingot), new McoSimpleMaterial("shovel_head_iron",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(250).setSpeedFlat(6f).setHarvestLevel(2).setEnchFlat(14).setAttackDmgFlat(2f).addRepairStack(new ItemStack(Items.iron_ingot), 0.25f));
		
		RecipesTools.registerShovelHeadMaterial(new ItemStack(Items.gold_ingot), new McoSimpleMaterial("shovel_head_gold",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(32).setSpeedFlat(12f).setHarvestLevel(0).setEnchFlat(22).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Items.gold_ingot), 0.25f));
		
		RecipesTools.registerShovelHeadMaterial(new ItemStack(Items.diamond), new McoSimpleMaterial("shovel_head_diamond",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(1561).setSpeedFlat(8f).setHarvestLevel(3).setEnchFlat(10).setAttackDmgFlat(3f).addRepairStack(new ItemStack(Items.diamond), 0.25f));
	}
	
	private static void addHoeHeads() {
		RecipesTools.registerHoeHeadMaterial(new ItemStack(Blocks.planks), new McoSimpleMaterial("hoe_head_wood",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(59).setSpeedFlat(2f).setHarvestLevel(0).setEnchFlat(15).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Blocks.planks), 0.25f));
		
		RecipesTools.registerHoeHeadMaterial(new ItemStack(Items.flint), new McoSimpleMaterial("hoe_head_stone",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(131).setSpeedFlat(4f).setHarvestLevel(1).setEnchFlat(5).setAttackDmgFlat(1f).addRepairStack(new ItemStack(Items.flint), 0.25f));
		
		RecipesTools.registerHoeHeadMaterial(new ItemStack(Items.iron_ingot), new McoSimpleMaterial("hoe_head_iron",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(250).setSpeedFlat(6f).setHarvestLevel(2).setEnchFlat(14).setAttackDmgFlat(2f).addRepairStack(new ItemStack(Items.iron_ingot), 0.25f));
		
		RecipesTools.registerHoeHeadMaterial(new ItemStack(Items.gold_ingot), new McoSimpleMaterial("hoe_head_gold",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(32).setSpeedFlat(12f).setHarvestLevel(0).setEnchFlat(22).setAttackDmgFlat(0f).addRepairStack(new ItemStack(Items.gold_ingot), 0.25f));
		
		RecipesTools.registerHoeHeadMaterial(new ItemStack(Items.diamond), new McoSimpleMaterial("hoe_head_diamond",
				IToolMod.FLAG_MOD_DURAB_FLAT | IToolMod.FLAG_MOD_SPEED_FLAT | IToolMod.FLAG_MOD_ENCH_FLAT | IToolMod.FLAG_MOD_DMG_FLAT)
				.setDurabFlat(1561).setSpeedFlat(8f).setHarvestLevel(3).setEnchFlat(10).setAttackDmgFlat(3f).addRepairStack(new ItemStack(Items.diamond), 0.25f));
	}
}
