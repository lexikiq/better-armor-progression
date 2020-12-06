package io.github.lexikiq.betterarmor.items;

import io.github.lexikiq.betterarmor.BArmorMod;
import lombok.Getter;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum RegisterItems {
    MARROW, // marrow set crafting ingredient
    VOID_FRAGMENT, // void set crafting ingredient
    DRAGONS_ESSENCE, // withering set crafting ingredient (dragon drop) // TODO: ask avery for better item name
    NECRO_DUST // withering set crafting ingredient (wither drop)
    ;

    private @Getter final Item item;

    RegisterItems(Item item) {
        this.item = item;
    }

    RegisterItems(FabricItemSettings itemSettings) {
        this.item = new Item(itemSettings.group(BArmorMod.BARMOR_GROUP));
    }

    RegisterItems() {
        this.item = new Item(new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
    }

    public void register() {
        Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, name().toLowerCase()), item);
    }
}
