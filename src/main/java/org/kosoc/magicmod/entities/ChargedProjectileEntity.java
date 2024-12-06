package org.kosoc.magicmod.entities;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kosoc.magicmod.renderer.entites.ChargedProjectileRenderer;

public class ChargedProjectileEntity extends ProjectileEntity {
    private int level; // Charge level for speed and damage scaling.

    public ChargedProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ChargedProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world, LivingEntity owner, int level) {
        super(entityType, world);
        this.setOwner(owner);
        this.level = level;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity target) {
            // Apply true damage based on charge level
            target.damage(getWorld().getDamageSources().magic(), level * 3.0f); // Damage scales with level
        }
        this.discard(); // Remove projectile after hitting.
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.discard(); // Remove on collision with any surface or object.
    }

    @Override
    protected void initDataTracker() {
        // Initialize data tracker for networking purposes (if needed).
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        // Packet to synchronize spawning with the client.
        return super.createSpawnPacket(); // Use Fabric's `EntitySpawnPacket` or similar.
    }

    public void setVelocityFromLevel(LivingEntity owner, int level) {
        float speed = Math.min(level * 2.0f, 6.0f); // Scale speed with charge level, capped at 6 blocks/s.
        Vec3d direction = owner.getRotationVec(1.0f).normalize().multiply(speed);
        this.setVelocity(direction);
    }
}