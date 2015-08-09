package celestibytes.magicandcorruption.pre.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import celestibytes.magicandcorruption.pre.client.render.RenderCompost;
import celestibytes.magicandcorruption.pre.client.render.RenderTool;
import celestibytes.magicandcorruption.pre.handler.event.ItemTooltips;
import celestibytes.magicandcorruption.pre.handler.event.RenderHud;
import celestibytes.magicandcorruption.pre.init.ModItems;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	private String clientPlayerName = null;
	
	@Override
	public String getClientPlayerName() {
		if(clientPlayerName == null) {
			clientPlayerName = FMLClientHandler.instance().getClient().getSession().getUsername();
			if(clientPlayerName == null) {
				clientPlayerName = "null";
			}
		}
		
		return clientPlayerName;
	}
	
	private static RenderTool rt = new RenderTool();
	
	@Override
	public void registerRendering() {
		MinecraftForgeClient.registerItemRenderer(ModItems.sword, rt);
		MinecraftForgeClient.registerItemRenderer(ModItems.pickaxe, rt);
		MinecraftForgeClient.registerItemRenderer(ModItems.axe, rt);
		MinecraftForgeClient.registerItemRenderer(ModItems.shovel, rt);
		MinecraftForgeClient.registerItemRenderer(ModItems.hoe, rt);
		RenderingRegistry.registerBlockHandler(new RenderCompost());
	}
	
	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new RenderHud());
		MinecraftForge.EVENT_BUS.register(new ItemTooltips());
	}
}
