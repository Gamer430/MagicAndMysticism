package org.kosoc.magicmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kosoc.magicmod.interfaces.IPlayerData;
import org.kosoc.magicmod.items.ModItemRegister;
import org.kosoc.magicmod.storage.DataStorage;

import java.util.Random;
import java.util.UUID;

public class Magicmod implements ModInitializer {
    private static final UUID TARGET_PLAYER_UUID = UUID.fromString("8ee54b35-adce-4c61-8846-a4e698915406"); // Replace with the actual UUID of the player.


    @Override
    public void onInitialize() {
        ModItemRegister.initialize();
        ServerTickEvents.END_SERVER_TICK.register(this::checkNearbyEntities);
        ServerTickEvents.END_SERVER_TICK.register(this::checkNearbyEntities);
        ServerPlayConnectionEvents.JOIN.register(this::onPlayerJoin);
    }

    private void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer server) {
        PlayerEntity player = serverPlayNetworkHandler.getPlayer();
        onPlayerJoin2(player);
    }


    private void checkNearbyEntities(MinecraftServer server){
        PlayerEntity targetPlayer = server.getPlayerManager().getPlayer(TARGET_PLAYER_UUID);

        if (targetPlayer == null) {
            return; // Player is not online
        }

        World world = targetPlayer.getWorld();
        Vec3d playerPos = targetPlayer.getPos();
        double radius = 10.0; // Define the radius for checking entities
        Box area = new Box(playerPos.add(-radius*2, -radius*2, -radius*2), playerPos.add(radius*2, radius*2, radius*2));

        for(Entity entity : world.getOtherEntities(null, area)){
            if(!(entity instanceof PlayerEntity)) return;
            IPlayerData playerData = (IPlayerData) entity;
            NbtCompound nbt = playerData.getPersistantData();
            boolean inManaZone = playerPos.isInRange(entity.getPos(), radius);

            nbt.putBoolean("inManaZone", inManaZone);

        }

    }

    private void onPlayerJoin2(PlayerEntity player) {
        // Example action: Send the player a welcome message
        IPlayerData playerData = ((IPlayerData) player);
        NbtCompound nbt = playerData.getPersistantData();
        int randomValue = getRandomByTens(500,2000);
        if (player.getUuid().equals(TARGET_PLAYER_UUID)) {
            DataStorage.setMaxMana(playerData, 5000);
            nbt.putBoolean("isWannabe", true);
        } else {
            DataStorage.setMaxMana(playerData, randomValue);
        }
    }

    public static int getRandomByTens(int min, int max) {
        Random random = new Random();
        // Calculate the number of possible values
        int range = (max - min) / 10 + 1;
        // Generate a random index
        int randomIndex = random.nextInt(range);
        // Map the index to the actual value
        return min + (randomIndex * 10);
    }
}
