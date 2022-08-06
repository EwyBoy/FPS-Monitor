package com.ewyboy.fps;

import com.ewyboy.fps.cleint.Display;
import com.ewyboy.fps.cleint.Keybindings;
import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.util.Translation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

import static com.ewyboy.fps.FpsMonitor.MOD_ID;

@Mod(MOD_ID)
public class FpsMonitor {

    public static final String MOD_ID = "fps";
    public static final String NAME = "FPS Monitor";

    public FpsMonitor() {
        ignoreServerOnly();
        Settings.setup();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> this :: clientStart);
    }

    @OnlyIn(Dist.CLIENT)
    public void clientStart() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Keybindings :: onRegisterKeyBinds);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new Display());
        MinecraftForge.EVENT_BUS.register(new Keybindings());
    }

    //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
    private void ignoreServerOnly() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> "You Can Write Whatever The Fuck You Want Here", (YouCanWriteWhatEverTheFuckYouWantHere, ICreatedSlimeBlocks2YearsBeforeMojangDid) -> ICreatedSlimeBlocks2YearsBeforeMojangDid)
        );
    }

}
