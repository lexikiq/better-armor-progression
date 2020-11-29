package io.github.lexikiq.betterarmor;

import io.github.lexikiq.betterarmor.utils.PlayerUtils;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Lazy;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
	VOID("void", 30, new int[]{3, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0.0F, 1, () -> {
		return Ingredient.ofItems(RegisterItems.VOID_FRAGMENT);
	}){
		final double range = 1.0;
		boolean set_range = true;

		@Override
		public void armorTick(World world, Entity entity) {
			if (!set_range) {return;}
			PlayerUtils.setRange((LivingEntity) entity, range);
			set_range = false;
//			StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.WATER_BREATHING, 8, 0, false, false);
//			LivingEntity player = (LivingEntity) entity;
//			{
//				player.addStatusEffect(effect);
//			}

		}

		@Override
		public void noArmorTick(World world, Entity entity) {
			set_range = true;
			PlayerUtils.setRange((LivingEntity) entity, null);
		}

		@Override
		public MutableText getTooltip(int n, Object ... args) {
			if (n == 1) {
				args = new Object[]{range};
			}
			return super.getTooltip(n, args);
		}


	};

	protected static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	protected final @Getter @Environment(EnvType.CLIENT) String name;
	protected final int durabilityMultiplier;
	protected final int[] protectionAmounts;
	protected final @Getter int enchantability;
	protected final @Getter SoundEvent equipSound;
	protected final @Getter float toughness;
	protected final @Getter float knockbackResistance;
	protected final int tooltips;
	protected final Lazy<Ingredient> repairIngredientSupplier;

	ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, int tooltips, Supplier<Ingredient> supplier)
	{
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.tooltips = tooltips;
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

	public Ingredient getRepairIngredient()
	{
		return this.repairIngredientSupplier.get();
	}

	public void armorTick(World world, Entity entity) {}

	public void noArmorTick(World world, Entity entity) {}

	public MutableText getTooltip(int n, Object ... args) {
		return new TranslatableText("item."+BArmorMod.MOD_ID+"."+this.name.toLowerCase()+".tip"+n, args).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xb37fc9)));
	}

	public List<MutableText> getTooltips() {
		List<MutableText> output = new ArrayList<>();
		if (tooltips == 0) {return output;}
		output.add(new TranslatableText("barmorprog.bonus").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x8e51a8)).withBold(true)));
		for (int i = 1; i <= tooltips; i++) {
			output.add(getTooltip(i));
		}
		return output;
	}
}