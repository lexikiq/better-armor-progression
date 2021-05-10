package io.github.lexikiq.betterarmor.items;

import io.github.lexikiq.betterarmor.BArmorMod;
import lombok.AllArgsConstructor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public enum RegisterItems implements ItemConvertible {
    MARROW, // marrow set crafting ingredient
    VOID_FRAGMENT, // void set crafting ingredient
    DRAGONS_ESSENCE, // withering set crafting ingredient (dragon drop) // TODO: ask avery for better item name
    NECRO_DUST, // withering set crafting ingredient (wither drop)
    OBSIDIAMOND // obsidian set crafting ingredient
    ;

    private final @NotNull Item item;
    public @NotNull Item asItem() {
        return item;
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
