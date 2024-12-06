package org.kosoc.magicmod.renderer.entites;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.kosoc.magicmod.entities.ChargedProjectileEntity;
import org.kosoc.magicmod.renderer.instanced.InstanceRenders;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;

public class ChargedProjectileRenderer extends EntityRenderer<ChargedProjectileEntity> {

    public ChargedProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ChargedProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light) {
        // Calculate position
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        // Fetch the world object
        var world = entity.getWorld();

        // Scale data (adjust based on your needs)
        float scale = 0.5f + (float) world.random.nextGaussian() * 0.1f;

        // Particle logic
        WorldParticleBuilder.create(InstanceRenders.ARCANE)
                .enableForcedSpawn()
                .setSpinData(SpinParticleData.create((float) (world.random.nextGaussian() / 5f)).build())
                .setScaleData(GenericParticleData.create(scale, 0f).setEasing(Easing.CIRC_OUT).build())
                .setTransparencyData(GenericParticleData.create(1f).build())
                .setColorData(
                        ColorParticleData.create(new Color(0xD400FF), new Color(0xFF00A4))
                                .setEasing(Easing.CIRC_OUT)
                                .build()
                )
                .enableNoClip()
                .setLifetime(20)
                .spawn(
                        world,
                        x + world.random.nextGaussian() / 20f,
                        y + (entity.getHeight() / 2f) + world.random.nextGaussian() / 20f,
                        z + world.random.nextGaussian() / 20f
                );

        // Call the super method to maintain any additional rendering behavior
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ChargedProjectileEntity entity) {
        // Return null since this entity uses particles instead of a texture
        return null;
    }
}
