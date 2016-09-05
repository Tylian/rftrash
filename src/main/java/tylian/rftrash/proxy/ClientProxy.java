package tylian.rftrash.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tylian.rftrash.ModBlocks;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModBlocks.initModels();
	}
}
