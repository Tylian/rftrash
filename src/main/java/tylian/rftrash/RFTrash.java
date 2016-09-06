package tylian.rftrash;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tylian.rftrash.proxy.CommonProxy;

@Mod(modid = RFTrash.MODID, name = RFTrash.MODID, dependencies = "required-after:Forge@[11.16.0.1865,)", useMetadata = true)
public class RFTrash {
	public static final String MODID = "rftrash";
	public static final String VERSION = "%VERSION%";

	public static final String CLIENT_PROXY = "tylian.rftrash.proxy.ClientProxy";
	public static final String SERVER_PROXY = "tylian.rftrash.proxy.ServerProxy";

	public static final int GUI_RFTRASH = 0;

	@SidedProxy(clientSide = RFTrash.CLIENT_PROXY, serverSide = RFTrash.SERVER_PROXY)
	public static CommonProxy proxy;

	@Instance
	public static RFTrash instance;

	public static Logger logger;

	public static CreativeTabs creativeTab = new CreativeTabs("RFTrash") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.rfTrashCan);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
