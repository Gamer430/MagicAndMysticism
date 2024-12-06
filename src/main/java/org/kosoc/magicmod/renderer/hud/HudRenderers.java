package org.kosoc.magicmod.renderer.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.util.Identifier;
import org.kosoc.magicmod.interfaces.IPlayerData;
import org.kosoc.magicmod.storage.DataStorage;

public class HudRenderers {
 /*
    private static final Identifier MANA_BAR_TEXTURE = new Identifier("magicmod", "textures/gui/mana_bar.png");

    public static void renderManaBar(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Get the player's current mana and max mana (replace with your actual mana logic)
        float currentMana = DataStorage.getTotalMana((IPlayerData) client.player);
        float maxMana = DataStorage.getMaxMana((IPlayerData) client.player);

        // Calculate mana bar width
        int barWidth = 182; // Same as experience bar
        int filledWidth = (int) ((double) currentMana / maxMana * barWidth);

        // Draw the background bar
        RenderSystem.setShaderTexture(0, MANA_BAR_TEXTURE);
        DrawableHelper.drawTexture(matrices, screenWidth / 2 - barWidth / 2, screenHeight - 32, 0, 0, barWidth, 5, 256, 256);

        // Draw the filled portion
        DrawableHelper.drawTexture(matrices, screenWidth / 2 - barWidth / 2, screenHeight - 32, 0, 5, filledWidth, 5, 256, 256);

        // Optionally: Draw the mana value as text
        client.textRenderer.drawWithShadow(matrices, currentMana + "/" + maxMana, screenWidth / 2 - 91, screenHeight - 42, 0xFFFFFF);
    }
  */

}
