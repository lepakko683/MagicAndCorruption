package celestibytes.magicandcorruption.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class LoadingPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		MagicAndCorruption_ASM.isObfuscatedEnv();
		return new String[] {
			"celestibytes.magicandcorruption.asm.cts.CT_ItemStack",
			"celestibytes.magicandcorruption.asm.cts.CT_Item",
			"celestibytes.magicandcorruption.asm.cts.CT_InvStackSize",
			"celestibytes.magicandcorruption.asm.cts.CT_PacketBuffer",
			"celestibytes.magicandcorruption.asm.cts.CT_RenderItem",
			"celestibytes.magicandcorruption.asm.cts.CT_NetHandlerPlayServer",
			"celestibytes.magicandcorruption.asm.cts.CT_DebugOutput",
			//"celestibytes.magicandcorruption.asm.cts.CT_InventoryBasic",
			"celestibytes.magicandcorruption.asm.cts.CT_Container"
		};
	}

	@Override
	public String getModContainerClass() {
		return "celestibytes.magicandcorruption.asm.MagicAndCorruption_ASM";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
