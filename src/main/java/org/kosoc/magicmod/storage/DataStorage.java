package org.kosoc.magicmod.storage;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ExperienceCommand;
import net.minecraft.text.Text;
import org.kosoc.magicmod.interfaces.IPlayerData;
import org.kosoc.magicmod.items.ModItemRegister;
import org.kosoc.magicmod.items.itemJavas.personalStaffItem;
import org.kosoc.magicmod.items.itemJavas.staffItem;

import java.util.function.Predicate;
import java.util.logging.Logger;

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
        } else if (getTotalMana(player) < maxMana && nbt.getBoolean("isWannabe")) {
            addMana(player, maxMana);
        }else if(getTotalMana(player) < maxMana && !nbt.getBoolean("inManaZone")){
            tick = 0;
            addMana(player, maxMana / 1000);
        }else if(getTotalMana(player) < maxMana){
            tick = 0;
            addMana(player, maxMana / 10);
        }
        nbt.putInt("rechargeTick", tick);
        return tick;
    }
    public static void cycle(PlayerEntity player){
        IPlayerData playerData = (IPlayerData) player;
        NbtCompound nbt = playerData.getPersistantData();
        int cycleInt = nbt.getInt("cycleNum");
        cycleInt += 1;
        if(player.getMainHandStack().getItem() instanceof personalStaffItem && cycleInt > 16){
            cycleInt = 0;
        }else if (player.getMainHandStack().getItem() instanceof staffItem && cycleInt > 6){
            cycleInt = 0;
        }else{
            cycleInt = 0;
        }
        System.out.println(cycleInt);
        nbt.putInt("cycleNum", cycleInt);
    }

    public static int getSpellNum(IPlayerData playerData){
        return playerData.getPersistantData().getInt("cycleNum");
    }
}
