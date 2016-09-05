package tylian.rftrash;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tylian.rftrash.blocks.rftrash.RFTrashBlock;

public class ModBlocks {

	public static RFTrashBlock rfTrashCan;

	public static void init() {
		rfTrashCan = new RFTrashBlock();
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		rfTrashCan.initModel();
	}
}
