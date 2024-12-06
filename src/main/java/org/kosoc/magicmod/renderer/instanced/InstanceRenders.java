package org.kosoc.magicmod.renderer.instanced;


import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.kosoc.magicmod.renderer.particles.ArcaneParticle;
import org.kosoc.magicmod.renderer.vfx.ArcaneParticleType;

public class InstanceRenders {
    public static ArcaneParticleType ARCANE = new ArcaneParticleType();

    public static Identifier id(String string) {
        return new Identifier("magicmod", string);
    }

    public static void initClientSideEffects(){
        ParticleFactoryRegistry.getInstance().register(ARCANE, ArcaneParticleType.Factory::new);
        ARCANE = Registry.register(Registries.PARTICLE_TYPE, InstanceRenders.id("arcane"), ARCANE);

    }

}
