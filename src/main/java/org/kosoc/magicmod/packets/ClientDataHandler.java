package org.kosoc.magicmod.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class ClientDataHandler {
    public static void requestDataFromServer(String string) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeString(string); // Add any data you need to send (if necessary)

        ClientPlayNetworking.send(ModPackets.REQUEST_DATA_PACKET, buffer);
    }
}
