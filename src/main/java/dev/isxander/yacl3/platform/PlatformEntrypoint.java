package dev.isxander.yacl3.platform;

import dev.isxander.yacl3.gui.image.YACLImageReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("yacl_reforged")
public class PlatformEntrypoint {
    public PlatformEntrypoint() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Client.initialize();
        }
    }
    private static final class Client {
        private static void initialize() {
            YACLConfig.HANDLER.load();
            RegisterClientReloadListenersEvent.BUS.addListener(event ->
                    event.registerReloadListener(new YACLImageReloadListener()));
        }
    }
}
