package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import lombok.Getter;

@Getter
public class DragonArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{4, 7, 9, 4};

	@Override
	public float getToughness() {
		return 4f;
	}

	@Override
	public float getKnockbackResistance() {
		return .2f;
	}

	@Override
	public int getDurabilityMultiplier() {
		return 42;
	}
}
