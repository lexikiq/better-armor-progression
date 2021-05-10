package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import io.github.lexikiq.betterarmor.items.RegisterItems;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

@Getter
@NoArgsConstructor
public class ObsidianArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{4, 9, 10, 5};

	@Override
	public void armorTick(World world, Entity entity, int count) {
		StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.SLOWNESS, 8, count-1, false, false);
		LivingEntity player = (LivingEntity) entity;
		player.addStatusEffect(effect);
	}

	@Override
	public String getName() {
		return "obsidian";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 48;
	}

	@Override
	public int getEnchantability() {
		return 9;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
	}

	@Override
	public float getToughness() {
		return 3.0F;
	}

	@Override
	public float getKnockbackResistance() {
		return 0.175F;
	}

	@Override
	public int getSetBonuses() {
		return 0;
	}

	@Override
	public int getPieceBonuses() {
		return 1;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(RegisterItems.OBSIDIAMOND); // TODO: obsidiamond
	}

	@Override
	public boolean isPartialSet() {
		return true;
	}

	@Override
	public boolean noPearlDamage() {
		return false;
	}

	@Override
	public float getRangedMultiplier() {
		return 1.0F;
	}
}
