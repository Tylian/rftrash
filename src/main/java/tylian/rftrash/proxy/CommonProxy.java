package tylian.rftrash.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tylian.rftrash.ModBlocks;
import tylian.rftrash.ModCrafting;
import tylian.rftrash.RFTrash;
import tylian.rftrash.compat.CompatibilityHandler;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		ModBlocks.init();
		ModCrafting.init();

		CompatibilityHandler.registerWaila();
	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(RFTrash.instance, new GuiProxy());

	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}