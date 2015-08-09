package celestibytes.magicandcorruption.pre.proxy;

import net.minecraftforge.common.MinecraftForge;
import celestibytes.magicandcorruption.pre.handler.event.EntityJoinWorld;
import celestibytes.magicandcorruption.pre.handler.event.LivingHurt;
import celestibytes.magicandcorruption.pre.handler.event.RenderHud;

public class CommonProxy {
	
	public String getClientPlayerName() {
		return null;
	}
	
	public void registerRendering() {}
	
	public void registerEventHandlers() {
//		MinecraftForge.EVENT_BUS.register(new LivingHurt());
//		MinecraftForge.EVENT_BUS.register(new EntityJoinWorld());
	}
	
	
}
