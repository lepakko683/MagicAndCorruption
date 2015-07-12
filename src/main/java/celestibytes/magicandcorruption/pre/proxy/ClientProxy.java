package celestibytes.magicandcorruption.pre.proxy;

import celestibytes.magicandcorruption.pre.client.render.RenderCompost;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRendering() {
		RenderingRegistry.registerBlockHandler(new RenderCompost());
	}
}
