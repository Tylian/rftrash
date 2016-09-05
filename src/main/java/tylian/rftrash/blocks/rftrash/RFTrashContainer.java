package tylian.rftrash.blocks.rftrash;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RFTrashContainer extends Container {
	private RFTrashTileEntity te;

	public RFTrashContainer(IInventory playerInventory, RFTrashTileEntity te) {
		this.te = te;

		addOwnSlots();
		addPlayerSlots(playerInventory);
	}

	private void addPlayerSlots(IInventory playerInventory) {
		for (int col = 0; col < 9; col++) {
			// Slots for the main inventory
			for (int row = 0; row < 3; row++) {
				int x = 10 + col * 18;
				int y = 70 + row * 18;
				this.addSlotToContainer(new Slot(playerInventory, 9 + col + row * 9, x, y));
			}

			// Slots for the hotbar
			int x = 10 + col * 18;
			int y = 70 + 58;
			this.addSlotToContainer(new Slot(playerInventory, col, x, y));
		}
	}

	private void addOwnSlots() {
		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 82;
		int y = 19;

		// Add our own slots
		int slotIndex = 0;
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
			slotIndex++;
			x += 18;
		}
	}

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < RFTrashTileEntity.SIZE) {
				if (!this.mergeItemStack(itemstack1, RFTrashTileEntity.SIZE, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, RFTrashTileEntity.SIZE, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
}
