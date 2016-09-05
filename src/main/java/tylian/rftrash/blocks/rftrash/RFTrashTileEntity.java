package tylian.rftrash.blocks.rftrash;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class RFTrashTileEntity extends TileEntity implements IEnergyProvider, ITickable {
	public static final int SIZE = 1;

	protected EnergyStorage storage = new EnergyStorage(16000);

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		storage.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		storage.writeToNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		return compound;
	}

	// IInventory
	private ItemStackHandler itemStackHandler = new ItemStackHandler() {
		@Override
		protected void onContentsChanged(int slot) {
			ItemStack item = this.getStackInSlot(slot);
			if (item != null) {
				this.setStackInSlot(slot, null);
				storage.receiveEnergy(item.stackSize, false);

				IBlockState state = worldObj.getBlockState(getPos());
				worldObj.notifyBlockUpdate(getPos(), state, state, 3);
			}
			markDirty();
		}
	};

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) itemStackHandler;
		}
		return super.getCapability(capability, facing);
	}

	// IEnergyProvider
	@Override
	public int getEnergyStored(EnumFacing from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	// ITickable
	@Override
	public void update() {
		handleSendEnergy();
	}

	private void handleSendEnergy() {
		int energyStored = getEnergyStored(EnumFacing.DOWN);

		// handle sending power out
		for (EnumFacing dir : EnumFacing.values()) {
			BlockPos o = getPos().offset(dir);
			TileEntity te = worldObj.getTileEntity(o);
			if (te instanceof IEnergyReceiver) {
				IEnergyConnection connection = (IEnergyConnection) te;
				EnumFacing opposite = dir.getOpposite();
				if (connection.canConnectEnergy(opposite)) {
					int received = ((IEnergyReceiver) te).receiveEnergy(opposite, energyStored, false);
					energyStored -= storage.extractEnergy(received, false);
					if (energyStored <= 0) {
						break;
					}
				}
			}
		}
	}
}
