package tylian.rftrash;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void init() {
		addBlockRecipies();
	}
	
	public static void addBlockRecipies() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.rfTrashCan),
				"SSS",
				"CHC",
				"CRC",
				'S', Blocks.STONE,
				'C', Blocks.COBBLESTONE,
				'H', Blocks.CHEST,
				'R', Items.REDSTONE);
	}
}
