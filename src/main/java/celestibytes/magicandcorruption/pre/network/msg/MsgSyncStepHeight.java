package celestibytes.magicandcorruption.pre.network.msg;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MsgSyncStepHeight implements IMessageHandler<MsgSyncStepHeight, IMessage>, IMessage {
	
	private float stepHeight;
	
	public MsgSyncStepHeight() {}
	
	public MsgSyncStepHeight(float stepHeight) {
		this.stepHeight = stepHeight;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		stepHeight = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(stepHeight);
	}

	@Override
	public IMessage onMessage(MsgSyncStepHeight msg, MessageContext ctx) {
		FMLClientHandler.instance().getClient().thePlayer.stepHeight = msg.stepHeight;
		
		return null;
	}

}
