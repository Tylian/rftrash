package tylian.rftrash.blocks.rftrash;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;
import tylian.rftrash.RFTrash;

public class RFTrashGui extends GuiContainer {
	public static final int WIDTH = 180;
	public static final int HEIGHT = 152;

	public RFTrashTileEntity te;

	private static final ResourceLocation background = new ResourceLocation(RFTrash.MODID, "textures/gui/rftrash.png");

	public RFTrashGui(RFTrashTileEntity tileEntity, RFTrashContainer container) {
		super(container);

		te = tileEntity;

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int x = guiLeft + 90;
		int y = guiTop + 42;

		drawStringCentered(mc.fontRendererObj, I18n.format("rftrash.storedEnergy"), x, y, 0xFF303030);

		y += mc.fontRendererObj.FONT_HEIGHT;
		drawStringCentered(mc.fontRendererObj, String.format("%,d RF", te.getEnergyStored(null)), x, y, 0xFF303030);
	}

	private void drawStringCentered(FontRenderer fontRenderer, String text, int x, int y, int color) {
		fontRenderer.drawString(text, x - (fontRenderer.getStringWidth(text) / 2), y, color);
	}
}