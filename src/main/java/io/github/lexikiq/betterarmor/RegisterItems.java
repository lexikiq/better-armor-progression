package io.github.lexikiq.betterarmor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItems {
	public static final Item VOID_FRAGMENT = new Item(new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_HELMET = new ModArmorItem(ModArmorMaterial.VOID, EquipmentSlot.HEAD, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_CHESTPLATE = new ModArmorItem(ModArmorMaterial.VOID, EquipmentSlot.CHEST, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_LEGGINGS = new ModArmorItem(ModArmorMaterial.VOID, EquipmentSlot.LEGS, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item VOID_BOOTS = new ModArmorItem(ModArmorMaterial.VOID, EquipmentSlot.FEET, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));

	public static final Item BLAZE_HELMET = new ModArmorItem(ModArmorMaterial.BLAZE, EquipmentSlot.HEAD, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item BLAZE_CHESTPLATE = new ModArmorItem(ModArmorMaterial.BLAZE, EquipmentSlot.CHEST, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item BLAZE_LEGGINGS = new ModArmorItem(ModArmorMaterial.BLAZE, EquipmentSlot.LEGS, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item BLAZE_BOOTS = new ModArmorItem(ModArmorMaterial.BLAZE, EquipmentSlot.FEET, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));

	public static final Item STONE_HELMET = new ModArmorItem(ModArmorMaterial.STONE, EquipmentSlot.HEAD, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item STONE_CHESTPLATE = new ModArmorItem(ModArmorMaterial.STONE, EquipmentSlot.CHEST, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item STONE_LEGGINGS = new ModArmorItem(ModArmorMaterial.STONE, EquipmentSlot.LEGS, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));
	public static final Item STONE_BOOTS = new ModArmorItem(ModArmorMaterial.STONE, EquipmentSlot.FEET, new FabricItemSettings().group(BArmorMod.BARMOR_GROUP));

	public static void register(String namespace) {
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_fragment"), VOID_FRAGMENT);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_helmet"), VOID_HELMET);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_chestplate"), VOID_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_leggings"), VOID_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier(namespace, "void_boots"), VOID_BOOTS);

		Registry.register(Registry.ITEM, new Identifier(namespace, "blaze_helmet"), BLAZE_HELMET);
		Registry.register(Registry.ITEM, new Identifier(namespace, "blaze_chestplate"), BLAZE_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier(namespace, "blaze_leggings"), BLAZE_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier(namespace, "blaze_boots"), BLAZE_BOOTS);

		Registry.register(Registry.ITEM, new Identifier(namespace, "stone_helmet"), STONE_HELMET);
		Registry.register(Registry.ITEM, new Identifier(namespace, "stone_chestplate"), STONE_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier(namespace, "stone_leggings"), STONE_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier(namespace, "stone_boots"), STONE_BOOTS);
	}
}
