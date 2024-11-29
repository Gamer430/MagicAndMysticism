package org.kosoc.magicmod.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItemRegister {
    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier("magicmod", id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }
}
