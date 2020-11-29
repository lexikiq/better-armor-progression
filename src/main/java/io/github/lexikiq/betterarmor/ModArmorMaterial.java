package io.github.lexikiq.betterarmor;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.lexikiq.betterarmor.utils.PlayerUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Lazy;
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
	VOID("void", 30, new int[]{3, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0.0F, () -> {
		return Ingredient.ofItems(RegisterItems.VOID_FRAGMENT);
	}){
		@Override
		public void armorTick(World world, Entity entity) {
			PlayerUtils.setRange((LivingEntity) entity, 1.0);
//			StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.WATER_BREATHING, 8, 0, false, false);
//			LivingEntity player = (LivingEntity) entity;
//			{
//				player.addStatusEffect(effect);
//			}

		}

		@Override
		public void noArmorTick(World world, Entity entity) {
			PlayerUtils.setRange((LivingEntity) entity, null);
		}


	};

	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;

	ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier)
	{
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredientSupplier = new Lazy<>(supplier);
	}

	public int getDurability(EquipmentSlot slot)
	{
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}

	public int getProtectionAmount(EquipmentSlot slot)
	{
		return this.protectionAmounts[slot.getEntitySlotId()];
	}

	public int getEnchantability()
	{
		return this.enchantability;
	}

	public SoundEvent getEquipSound()
	{
		return this.equipSound;
	}

	public Ingredient getRepairIngredient()
	{
		return this.repairIngredientSupplier.get();
	}

	@Environment(EnvType.CLIENT)
	public String getName()
	{
		return this.name;
	}

	public float getToughness()
	{
		return this.toughness;
	}

	public float getKnockbackResistance()
	{
		return this.knockbackResistance;
	}

	public void armorTick(World world, Entity entity) {}

	public void noArmorTick(World world, Entity entity) {}
}