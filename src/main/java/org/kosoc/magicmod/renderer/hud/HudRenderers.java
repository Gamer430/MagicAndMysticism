package org.kosoc.magicmod.renderer.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.kosoc.magicmod.Magicmod;
import org.kosoc.magicmod.interfaces.IPlayerData;
import org.kosoc.magicmod.items.ModItemRegister;
import org.kosoc.magicmod.storage.DataStorage;

public class HudRenderers implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && client.world != null) {
            // Check if the player is holding the specific item
            if (client.player.getMainHandStack().getItem() == ModItemRegister.CustomStaff) {
                renderManaHud(context, client);
            }
        }
    }

    private static void renderManaHud(DrawContext context, MinecraftClient client) {
        MatrixStack matrices = context.getMatrices();
        final int TEXT_COLOR = 0xFFFFFF;
        final int MANA_COLOR = 0x07F1E9;
        PlayerEntity player = client.player;

        // Example values - replace these with your logic to get actual mana and spell slot
        float currentMana = DataStorage.getTotalMana((IPlayerData) player);
        float maxMana = DataStorage.getMaxMana((IPlayerData) player);
        int spellSlot = DataStorage.getSpellNum((IPlayerData) player);

        // Formatting the text
        Text spellSlotText = Text.literal("Spell Slot: " + spellSlot).formatted(Formatting.LIGHT_PURPLE);
        Text manaText = Text.literal("Mana: " + currentMana + "/" + maxMana).formatted(Formatting.AQUA);

        // Render text on the bottom left of the screen
        int x = 10; // X position
        int y = client.getWindow().getScaledHeight() - 40; // Y position for mana

        context.drawTextWithShadow(client.textRenderer, spellSlotText, x, y - 10, TEXT_COLOR);
        context.drawTextWithShadow(client.textRenderer, manaText, x, y, MANA_COLOR);
    }

}
