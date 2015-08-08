package celestibytes.magicandcorruption.pre.init;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameData;

public class PackItems {
	
	
	
	public static class MagicAndCorruption {
		
	}
	
	public static class Thaumcraft {
		private static Item getItem(String name) {
			return GameData.getItemRegistry().getObject("Thaumcraft:" + name);
		}
		/** Metadata: <pre>
		 *   0: Alumentum
		 *   1: Nitor
		 *   2: Thaumium Ingot
		 *   3: Quicksilver
		 *   4: Magic Tallow
		 *   5: ?
		 *   6: Amber
		 *   7: Enchanted Fabric
		 *   8: Vis Filter
		 *   9: Knowledge Fragment
		 *  10: Mirrored Glass
		 *  11: Tainted Goo
		 *  12: Taint Tendril
		 *  13: Jar Label
		 *  14: Salis Mundus
		 *  15: Primal Charm
		 *  16: Void metal Ingot
		 *  17: Void Seed
		 *  18: Gold Coin </pre>
		 */
		public static final Item itemResource = getItem("ItemResource");
		
		/** Metadata: <pre>
		 *   0: Air Shard
		 *   1: Fire Shard
		 *   2: Water Shard
		 *   3: Earth Shard
		 *   4: Order Shard
		 *   5: Entropy Shard
		 *   6: Balanced Shard </pre>
		 */
		public static final Item itemShard = getItem("ItemShard");
		public static final Item itemNugget = getItem("ItemNugget");
	}
	
	public static class Witchery {
		private static Item getItem(String name) {
			return GameData.getItemRegistry().getObject("witchery:" + name);
		}
		
		/** Metadata: <pre>
		 * 63: Rowan Berries
		 * </pre>
		 */
		public static Item itemIngredient = getItem("ingredient");
	}
	
	public static class Natura {
		private static Item getItem(String name) {
			return GameData.getItemRegistry().getObject("Natura:" + name);
		}
		
		public static final Item itemFlour = getItem("flour"); // TODO: use the correct name, the current is just to have eclipse not complain getItem being unused
	}
}
