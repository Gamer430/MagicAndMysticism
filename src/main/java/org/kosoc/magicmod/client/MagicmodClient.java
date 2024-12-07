package org.kosoc.magicmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.network.PacketByteBuf;
import org.kosoc.magicmod.entities.ModEntityRegsitry;
import org.kosoc.magicmod.handlers.KeybindHandler;
import org.kosoc.magicmod.packets.ModPackets;
import org.kosoc.magicmod.renderer.entites.ChargedProjectileRenderer;
import org.kosoc.magicmod.renderer.hud.HudRenderers;
import org.kosoc.magicmod.renderer.instanced.InstanceRenders;

public class MagicmodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        InstanceRenders.initClientSideEffects();
        KeybindHandler.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeybindHandler.cycleKey.wasPressed()) {
                PacketByteBuf buf = PacketByteBufs.create();
                ClientPlayNetworking.send(ModPackets.MOD_CYCLE_PACKET_ID, buf);
            }
        });
        new HudRenderers().onInitializeClient();
        EntityRendererRegistry.register(ModEntityRegsitry.CHARGED_PROJECTILE, ChargedProjectileRenderer::new);
    }


}
