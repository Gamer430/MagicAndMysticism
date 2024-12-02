package org.kosoc.magicmod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import org.kosoc.magicmod.items.ItemMaterials.LesserMaterial;
import org.kosoc.magicmod.items.ItemMaterials.StaffMaterial;
import org.kosoc.magicmod.items.itemJavas.personalStaffItem;
import org.kosoc.magicmod.items.itemJavas.staffItem;

public class ModItemRegister {

    public static final Item CustomStaff = new personalStaffItem(StaffMaterial.INSTANCE, 0, -3F, BlockTags.PICKAXE_MINEABLE, new FabricItemSettings());
    public static final Item NormalStaff = new staffItem(LesserMaterial.INSTANCE, 0, -3F, BlockTags.PICKAXE_MINEABLE, new FabricItemSettings());
    public static void initialize() {
        Registry.register(Registries.ITEM, new Identifier("magicmod", "true_magic_staff"), CustomStaff);
        Registry.register(Registries.ITEM, new Identifier("magicmod", "magic_staff"), NormalStaff);
    }

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier("magicmod", id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }
}
