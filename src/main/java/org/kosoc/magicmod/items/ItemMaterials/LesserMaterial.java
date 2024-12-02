package org.kosoc.magicmod.items.ItemMaterials;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class LesserMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 999999999;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 2.0F;
    }

    @Override
    public float getAttackDamage() {
        return 3;
    }

    @Override
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 50;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    public static final LesserMaterial INSTANCE = new LesserMaterial();
}
