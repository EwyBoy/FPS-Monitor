package com.ewyboy.fps;

import com.ewyboy.fps.cleint.FpsDisplay;
import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.util.ModLogger;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.server.gui.PlayerListComponent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

import static com.ewyboy.fps.FpsMonitor.MOD_ID;

@Mod(MOD_ID)
public class FpsMonitor {

    public static final String NAME = "FPS Monitor";
    public static final String MOD_ID = "fps";

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
        MinecraftForge.EVENT_BUS.register(new FpsDisplay());
    }

}
