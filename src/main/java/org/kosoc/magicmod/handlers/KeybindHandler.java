package org.kosoc.magicmod.handlers;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {
    public static KeyBinding cycleKey;

    public static void register(){
        cycleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.magicmod.cycle_functionality",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category.magicmod.controls"
        ));
    }
}
