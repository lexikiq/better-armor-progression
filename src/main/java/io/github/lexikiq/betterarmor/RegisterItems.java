package io.github.lexikiq.betterarmor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItems {
	public static final Item VOID_FRAGMENT = new Item(new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));

	public static final ArmorMaterial voidArmorMaterial = ModArmorMaterial.VOID;
	public static final Item VOID_HELMET = new ArmorItem(voidArmorMaterial, EquipmentSlot.HEAD, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_CHESTPLATE = new ArmorItem(voidArmorMaterial, EquipmentSlot.CHEST, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_LEGGINGS = new ArmorItem(voidArmorMaterial, EquipmentSlot.LEGS, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_BOOTS = new ArmorItem(voidArmorMaterial, EquipmentSlot.FEET, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));

	public static void register(String namespace) {
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_fragment"), VOID_FRAGMENT);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_helmet"), VOID_HELMET);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_chestplate"), VOID_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_leggings"), VOID_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_boots"), VOID_BOOTS);
	}
}
