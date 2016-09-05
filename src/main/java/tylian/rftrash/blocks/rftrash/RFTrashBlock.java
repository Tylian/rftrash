package tylian.rftrash.blocks.rftrash;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tylian.rftrash.RFTrash;
import tylian.rftrash.compat.waila.IWailaInfoProvider;
import tylian.rftrash.blocks.rftrash.RFTrashTileEntity;

public class RFTrashBlock extends Block implements ITileEntityProvider, IWailaInfoProvider {
	public RFTrashBlock() {
		super(Material.ROCK);
		setUnlocalizedName(RFTrash.MODID + ".rftrash");
		setRegistryName("rftrash");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(RFTrashTileEntity.class, RFTrash.MODID + "_rftrash");
		setCreativeTab(RFTrash.creativeTab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new RFTrashTileEntity();
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean isBlockNormalCube(IBlockState blockState) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}

		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof RFTrashTileEntity)) {
			return false;
		}

		player.openGui(RFTrash.instance, RFTrash.GUI_RFTRASH, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof RFTrashTileEntity) {
        	RFTrashTileEntity rfTrashTE = (RFTrashTileEntity) te;
        	tip.add(String.format("%s%,d / %,d RF", TextFormatting.GRAY, rfTrashTE.getEnergyStored(null), rfTrashTE.getMaxEnergyStored(null)));
        }
        return tip;
    }
}
