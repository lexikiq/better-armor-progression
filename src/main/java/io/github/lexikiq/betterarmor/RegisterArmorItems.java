package io.github.lexikiq.betterarmor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;

public class RegisterArmorItems {
    public static void register() {
        Arrays.stream(ModArmorMaterial.values()).forEach(material -> {
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, material.getName().toLowerCase()+"_helmet"), new ModArmorItem(material, EquipmentSlot.HEAD, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, material.getName().toLowerCase()+"_chestplate"), new ModArmorItem(material, EquipmentSlot.CHEST, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, material.getName().toLowerCase()+"_leggings"), new ModArmorItem(material, EquipmentSlot.LEGS, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, material.getName().toLowerCase()+"_boots"), new ModArmorItem(material, EquipmentSlot.FEET, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
        });
    }
}
