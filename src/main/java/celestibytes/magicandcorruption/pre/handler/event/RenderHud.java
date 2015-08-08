package celestibytes.magicandcorruption.pre.handler.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class RenderHud {
	
	private int regl = 0;
	
	@SubscribeEvent
	public void onEvent(RenderGameOverlayEvent.Pre e) {
//		System.out.println("event");
		if(e.type == ElementType.ALL) {
			if(regl == 20) {
				regl = 0;
//				System.out.println("All!");
			} else {
				regl++;
			}
		}
	}
}
