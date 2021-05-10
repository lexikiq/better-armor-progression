package io.github.lexikiq.betterarmor;

import io.github.lexikiq.betterarmor.items.ModArmorItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class RegisterArmorItems {
    public void register() {
        new Reflections(getClass().getPackage().getName()+".armors").getSubTypesOf(ModArmorMaterial.class).forEach(material -> {
            ModArmorMaterial mat;
            try {
                mat = material.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return;
            }
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, mat.getName().toLowerCase()+"_helmet"), new ModArmorItem(mat, EquipmentSlot.HEAD, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, mat.getName().toLowerCase()+"_chestplate"), new ModArmorItem(mat, EquipmentSlot.CHEST, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, mat.getName().toLowerCase()+"_leggings"), new ModArmorItem(mat, EquipmentSlot.LEGS, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
            Registry.register(Registry.ITEM, new Identifier(BArmorMod.MOD_ID, mat.getName().toLowerCase()+"_boots"), new ModArmorItem(mat, EquipmentSlot.FEET, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP)));
        });
    }
}
