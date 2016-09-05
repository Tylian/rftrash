package tylian.rftrash.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tylian.rftrash.RFTrash;
import tylian.rftrash.blocks.rftrash.RFTrashContainer;
import tylian.rftrash.blocks.rftrash.RFTrashGui;
import tylian.rftrash.blocks.rftrash.RFTrashTileEntity;

public class GuiProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		switch (ID) {
		case RFTrash.GUI_RFTRASH:
			return new RFTrashContainer(player.inventory, (RFTrashTileEntity) te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		switch (ID) {
		case RFTrash.GUI_RFTRASH:
			RFTrashTileEntity containerTileEntity = (RFTrashTileEntity) te;
			return new RFTrashGui(containerTileEntity, new RFTrashContainer(player.inventory, containerTileEntity));
		}
		return null;
	}
}