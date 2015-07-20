package celestibytes.magicandcorruption.pre.network;

import celestibytes.magicandcorruption.pre.Ref;
import celestibytes.magicandcorruption.pre.network.msg.MsgSyncStepHeight;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Ref.MOD_ID.toLowerCase().substring(0, 20));
	
	public static void init() {
		INSTANCE.registerMessage(MsgSyncStepHeight.class, MsgSyncStepHeight.class, 0, Side.CLIENT);
	}
}
