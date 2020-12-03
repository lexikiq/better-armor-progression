package io.github.lexikiq.betterarmor;

import lombok.Getter;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum RegisterItems {
    VOID_FRAGMENT(new Item(new FabricItemSettings().group(BArmorMod.BARMOR_GROUP))), // void set crafting ingredient
    NECRO_DUST(new Item(new FabricItemSettings().group(BArmorMod.BARMOR_GROUP))); // wither set crafting ingredient

    private @Getter final Item item;

    RegisterItems(Item item) {
        this.item = item;
    }

    public void register() {
        Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, name().toLowerCase()), item);
    }
}
