package tylian.rftrash.compat;

import net.minecraftforge.fml.common.Loader;
import tylian.rftrash.compat.waila.WailaCompatibility;

public class CompatibilityHandler {
    public static void registerWaila() {
        if (Loader.isModLoaded("Waila")) {
            WailaCompatibility.register();
        }
    }
}
