package org.kosoc.magicmod.storage;

import net.minecraft.nbt.NbtCompound;
import org.kosoc.magicmod.interfaces.IPlayerData;

public class DataStorage {
    public static void removeMana(IPlayerData player, float amount) {
        NbtCompound nbt = player.getPersistantData();
        float totalMana = nbt.getFloat("totalMana");
        totalMana = totalMana - amount;
        nbt.putFloat("totalMana", totalMana);
    }

    public static void addMana(IPlayerData player, float amount) {
        NbtCompound nbt = player.getPersistantData();
        float totalMana = nbt.getFloat("totalMana");
        float maxMana = nbt.getFloat("maxMana");
        totalMana += amount;
        if (totalMana > maxMana) totalMana = maxMana;
        nbt.putFloat("totalMana", totalMana);
    }

    public static float getTotalMana(IPlayerData player) {
        NbtCompound nbt = player.getPersistantData();
        return nbt.getFloat("totalMana");
    }

    public static void setMaxMana(IPlayerData player, float amount) {
        NbtCompound nbt = player.getPersistantData();
        float maxMana = nbt.getFloat("maxMana");
        maxMana = amount;
        nbt.putFloat("maxMana", maxMana);
    }

    public static float getMaxMana(IPlayerData player) {
        NbtCompound nbt = player.getPersistantData();
        float maxMana = nbt.getFloat("maxMana");
        return maxMana;
    }

    public static int getRechargeTicks(IPlayerData player) {
        NbtCompound nbt = player.getPersistantData();
        return nbt.getInt("rechargeTick");
    }

    public static int rechargeTick(IPlayerData player){
        NbtCompound nbt = player.getPersistantData();
        int tick = nbt.getInt("rechargeTick");
        float maxMana = nbt.getFloat("maxMana");
        if(tick < 20 && !nbt.getBoolean("isWannabe")){
            tick += 1;
        } else if (getTotalMana(player) < maxMana && !nbt.getBoolean("isWannabe")) {
            addMana(player, maxMana);
        }
        if(getTotalMana(player) < maxMana && !nbt.getBoolean("inManaZone")){
            addMana(player, maxMana / 1000);
        }else if(getTotalMana(player) < maxMana){
            addMana(player, maxMana / 10);
        }
        nbt.putInt("rechargeTick", tick);
        return tick;
    }
}
