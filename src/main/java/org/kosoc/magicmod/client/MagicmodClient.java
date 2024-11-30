package org.kosoc.magicmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import org.kosoc.magicmod.handlers.KeybindHandler;
import org.kosoc.magicmod.packets.ModPackets;

public class MagicmodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeybindHandler.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeybindHandler.cycleKey.wasPressed()) {
                PacketByteBuf buf = PacketByteBufs.create();
                ClientPlayNetworking.send(ModPackets.MOD_CYCLE_PACKET_ID, buf);
            }
        });
    }
}
