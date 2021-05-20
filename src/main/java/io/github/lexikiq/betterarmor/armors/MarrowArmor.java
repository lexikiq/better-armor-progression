package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import io.github.lexikiq.betterarmor.items.RegisterItems;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;

@Getter
@NoArgsConstructor
public class MarrowArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{2, 6, 7, 2};

	@Override
	public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object... args) {
		if (n == 1 && isSetBonus) { args=new Object[]{(int) ((getRangedMultiplier()-1)*100)}; }
		return super.getTooltip(n, slot, isSetBonus, args);
	}

	@Override
	public String getName() {
		return "marrow";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 20;
	}

	@Override
	public int getEnchantability() {
		return 10;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
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
		return 1;
	}

	@Override
	public int getPieceBonuses() {
		return 0;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(RegisterItems.MARROW);
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
		return 1.25F;
	}
}
