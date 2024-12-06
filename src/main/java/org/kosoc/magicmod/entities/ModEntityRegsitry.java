package org.kosoc.magicmod.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityRegsitry {
    public static final EntityType<ChargedProjectileEntity> CHARGED_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("magicmod", "charged_projectile"),
            EntityType.Builder.<ChargedProjectileEntity>create(ChargedProjectileEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.5f, 0.5f)
                    .build("charged_projectile")
    );

    public static void registerEntities() {
        // Call during initialization.
    }
}
