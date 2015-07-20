package celestibytes.magicandcorruption.pre.network;

import celestibytes.magicandcorruption.pre.network.msg.MsgSyncStepHeight;
import net.minecraft.entity.player.EntityPlayerMP;

public class NetworkHelper { // celestibytes/magicandcorruption/pre/network/NetworkHelper syncStepHeight (Lnet/minecraft/entity/player/EntityPlayerMP;)V (Lmw;)V
	
	public static void syncStepHeight(EntityPlayerMP plr) {
		PacketHandler.INSTANCE.sendTo(new MsgSyncStepHeight(plr.stepHeight), plr);
	}
}
