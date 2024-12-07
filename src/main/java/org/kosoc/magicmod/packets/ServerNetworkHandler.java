package org.kosoc.magicmod.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.kosoc.magicmod.interfaces.IPlayerData;
import org.kosoc.magicmod.storage.DataStorage;

public class ServerNetworkHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.REQUEST_DATA_PACKET, (server, player, handler, buf, responseSender) -> {
            String requestedData = buf.readString(); // Read the data from the packet if necessary

            server.execute(() -> {
                PacketByteBuf responseBuffer = PacketByteBufs.create();
                // Compute the value to send back
                if(requestedData.contains("max")){
                    float responseValue = DataStorage.getMaxMana((IPlayerData) player);
                    responseBuffer.writeFloat(responseValue);
                    responseBuffer.writeString("max");
                }else if(requestedData.contains("total")){
                    float responseValue = DataStorage.getTotalMana((IPlayerData) player);
                    responseBuffer.writeFloat(responseValue);
                    responseBuffer.writeString("total");
                }else if(requestedData.contains("cycle")){
                    int responseValue = DataStorage.getSpellNum((IPlayerData) player);
                    responseBuffer.writeInt(responseValue);
                    responseBuffer.writeString("cycle");
                }

                ServerPlayNetworking.send(player, ModPackets.RESPONSE_DATA_PACKET, responseBuffer);
            });
        });
    }
}
