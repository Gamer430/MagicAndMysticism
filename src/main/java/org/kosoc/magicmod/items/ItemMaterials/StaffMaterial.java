package org.kosoc.magicmod.items.ItemMaterials;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class StaffMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 999999999;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 16.0f;
    }

    @Override
    public float getAttackDamage() {
        return 8;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 50;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
    public static final StaffMaterial INSTANCE = new StaffMaterial();
}
