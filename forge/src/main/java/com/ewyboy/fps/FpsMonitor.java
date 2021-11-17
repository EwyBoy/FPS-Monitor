package com.ewyboy.fps;

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
        MinecraftForge.EVENT_BUS.register(new FPS());
    }

    public static final class FPS {

        // Percent to Hex

        @SubscribeEvent
        public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                renderOverlay(event.getMatrixStack());
            }
        }

        public void renderOverlay(MatrixStack stack) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.options.renderDebug) {
                return;
            }

            MainWindow window = mc.getWindow();

            String[] fps = mc.fpsString.split(" ");
            String currentFPS = fps[0] + " " + fps[1].toUpperCase();

            mc.font.drawShadow(stack, currentFPS, 2, 2, 0xff00ffff, false);

            String ping;

            NetworkPlayerInfo entry = Objects.requireNonNull(mc.player).connection.getPlayerInfo(mc.player.getUUID());

            if (entry != null) {
                ping = entry.getLatency() + " ms";
            }

            //ModLogger.info("100% :: " + fromPercent(100));
            //ModLogger.info("75% :: " + fromPercent(75));
            //ModLogger.info("50% :: " + fromPercent(50));
            //ModLogger.info("25% :: " + fromPercent(25));
            //ModLogger.info("0% :: " + fromPercent(0));

            if (entry != null) {
                mc.font.drawShadow(stack, currentFPS, 2, 12, 0xff00ffff, false);
            }



        }
    }

}
