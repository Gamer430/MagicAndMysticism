package org.kosoc.magicmod.items.itemJavas;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.kosoc.magicmod.entities.ChargedProjectileEntity;
import org.kosoc.magicmod.entities.ModEntityRegsitry;
import org.kosoc.magicmod.interfaces.IPlayerData;
import org.kosoc.magicmod.storage.DataStorage;

import java.util.UUID;

public class personalStaffItem extends ToolItem implements Vanishable {
    private final TagKey<Block> effectiveBlocks;
    protected final float miningSpeed;
    private final float attackDamage;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;


    public personalStaffItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, TagKey<Block> effectiveBlocks, Item.Settings settings) {
        super(toolMaterial, settings);
        this.attackDamage = (float)attackDamage + toolMaterial.getAttackDamage();
        this.effectiveBlocks = effectiveBlocks;
        this.miningSpeed = toolMaterial.getMiningSpeedMultiplier();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }


    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;
        int chargeTicks = this.getMaxUseTime(stack) - remainingUseTicks;
        if (player.hurtTime > 0) { // Player was recently hit
            player.stopUsingItem(); // Cancel charging
            return;
        }

        // Deduct mana per tick
        if (DataStorage.getTotalMana((IPlayerData) user) >= 5) {
            DataStorage.removeMana((IPlayerData) user, 5f);

            // Visual feedback while charging
            if (player.getWorld().isClient && remainingUseTicks % 20 != 0) {
                player.getWorld().addParticle(ParticleTypes.ENCHANT, player.getX(), player.getEyeY(), player.getZ(), 0, 0, 0);
            }

        } else {
            // Stop using if mana is insufficient
            user.stopUsingItem();
        }
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        int chargeTicks = this.getMaxUseTime(stack) - remainingUseTicks;

        if(player.hurtTime <= 0){
            applyBacklash(player, chargeTicks);
            return;
        }
        // Determine spell logic based on charge
        int selectedSpell = DataStorage.getSpellNum((IPlayerData) user); // Example: read the spell from NBT
        switch (selectedSpell) {
            case 1:
                castArcaneProjectile(world, player, chargeTicks);
                break;
            case 2:
                enableFlight(player, chargeTicks);
                break;
            default:
                player.sendMessage(Text.literal("No spell selected"), true);
        }
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(this.effectiveBlocks) ? this.miningSpeed : 1.0F;
    }


    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    public boolean isSuitableFor(BlockState state) {
        int i = this.getMaterial().getMiningLevel();
        if (i < 3 && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < 2 && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else {
            return i < 1 && state.isIn(BlockTags.NEEDS_STONE_TOOL) ? false : state.isIn(this.effectiveBlocks);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000; // Vanilla max use time (3,600 seconds)
    }

    private void castArcaneProjectile(World world, PlayerEntity player, int chargeTicks) {
        int level = Math.min(chargeTicks / 40, 15); // 3 seconds per level, capped at level 5.
        ChargedProjectileEntity projectile = new ChargedProjectileEntity(
                ModEntityRegsitry.CHARGED_PROJECTILE, world, player, level
        );
        projectile.setPosition(player.getX(), player.getEyeY() - 0.1, player.getZ());
        projectile.setVelocityFromLevel(player, level); // Use level to scale speed/damage.
        world.spawnEntity(projectile);
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    private void enableFlight(PlayerEntity player, int chargeTicks) {
        IPlayerData playerData = (IPlayerData) player;
        NbtCompound nbt = playerData.getPersistantData();
        boolean flightEnabled = nbt.getBoolean("flightEnabled");
        if(!flightEnabled){
            if(chargeTicks >= 600){
                player.getAbilities().allowFlying = true;
            }else player.getAbilities().allowFlying = false;
        }
    }

    private void applyBacklash(PlayerEntity player, int chargeTicks){
        IPlayerData playerData = (IPlayerData) player;
        NbtCompound nbt = playerData.getPersistantData();
        int spell = nbt.getInt("cycleNum");
        if(spell == 1){
            int level = Math.min(chargeTicks / 40, 15);
            player.damage(new DamageSource((RegistryEntry<DamageType>) DamageTypes.MAGIC), 2*level);
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            player.getWorld().addParticle(ParticleTypes.EXPLOSION, player.getX(), player.getEyeY(), player.getZ(), 0, 0, 0);
        }

    }


}
