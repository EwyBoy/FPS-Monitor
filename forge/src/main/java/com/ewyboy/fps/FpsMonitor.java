package com.ewyboy.fps;

import com.ewyboy.fps.cleint.Display;
import com.ewyboy.fps.cleint.Keybindings;
import com.ewyboy.fps.config.Settings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

import static com.ewyboy.fps.FpsMonitor.MOD_ID;

@Mod(MOD_ID)
public class FpsMonitor {

    public static final String MOD_ID = "fps";
    public static final String NAME = "FPS Monitor";

    public FpsMonitor() {
        ignoreServerOnly();
        Settings.setup();
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    // Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
    private void ignoreServerOnly() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(
                () -> FMLNetworkConstants.IGNORESERVERONLY,
                (YouCanWriteWhatEverTheFuckYouWantHere, ICreatedSlimeBlocks2YearsBeforeMojangDid) -> true)
        );
    }

    @SubscribeEvent
    public void clientRegister(FMLClientSetupEvent event) {
        Keybindings.setup();
        MinecraftForge.EVENT_BUS.register(new Display());
    }

}
