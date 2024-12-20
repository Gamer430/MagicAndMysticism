package org.kosoc.magicmod.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.kosoc.magicmod.storage.DataStorage;

public class ModPackets {
    public static Identifier MOD_CYCLE_PACKET_ID = new Identifier("magicmod", "cycleint");
    public static final Identifier REQUEST_DATA_PACKET = new Identifier("magicmod", "request_data");
    public static final Identifier RESPONSE_DATA_PACKET = new Identifier("magicmod", "response_data");


    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(MOD_CYCLE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                DataStorage.cycle(player);
            });
        });
    }
}
