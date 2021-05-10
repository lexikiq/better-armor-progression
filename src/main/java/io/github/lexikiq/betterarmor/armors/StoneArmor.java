package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Getter
@NoArgsConstructor
public class StoneArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{1, 1, 2, 1};

	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 4;
	}

	@Override
	public int getEnchantability() {
		return 9;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
	}

	@Override
	public float getToughness() {
		return 0.0F;
	}

	@Override
	public float getKnockbackResistance() {
		return 0.0F;
	}

	@Override
	public int getSetBonuses() {
		return 0;
	}

	@Override
	public int getPieceBonuses() {
		return 0;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(Items.STONE);
	}

	@Override
	public boolean isPartialSet() {
		return false;
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
