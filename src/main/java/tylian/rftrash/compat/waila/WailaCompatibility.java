package tylian.rftrash.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tylian.rftrash.blocks.rftrash.RFTrashBlock;

public class WailaCompatibility implements IWailaDataProvider {

	public static final WailaCompatibility INSTANCE = new WailaCompatibility();

	private WailaCompatibility() {}

	private static boolean registered;
	private static boolean loaded;

	public static void load(IWailaRegistrar registrar) {
		if (!registered) {
			throw new RuntimeException("Please register this handler using the provided method.");
		}
		if (!loaded) {
			registrar.registerHeadProvider(INSTANCE, RFTrashBlock.class);
			registrar.registerBodyProvider(INSTANCE, RFTrashBlock.class);
			registrar.registerTailProvider(INSTANCE, RFTrashBlock.class);
			loaded = true;
		}
	}

	public static void register() {
		if (registered)
			return;
		registered = true;
		FMLInterModComms.sendMessage("Waila", "register", "tylian.rftrash.compat.waila.WailaCompatibility.load");
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world,
			BlockPos pos) {
		return tag;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return tip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		Block block = accessor.getBlock();
		if (block instanceof IWailaInfoProvider) {
			return ((IWailaInfoProvider) block).getWailaBody(itemStack, tip, accessor, config);
		}
		return tip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return tip;
	}

}