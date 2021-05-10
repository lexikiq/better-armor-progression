package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import io.github.lexikiq.betterarmor.items.RegisterItems;
import io.github.lexikiq.betterarmor.utils.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class WitheringArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{4, 8, 9, 5};
	private static final double WITHER_CHANCE = 0.01; // 1%
	private static final double WITHER_SECONDS = 5;
	private static final int WITHER_AMPLIFIER = 1;

	private double getWitherChance(EquipmentSlot slot) {return getWitherChance(getIngredients(slot));}
	private double getWitherChance(double multiplier) {return WITHER_CHANCE *multiplier;}

	@Override
	public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object... args) {
		if (n == 1 && isSetBonus) { args=new Object[]{DF.format(100* Arrays.stream(INGREDIENTS).mapToDouble(this::getWitherChance).sum()), WITHER_SECONDS -1, WITHER_AMPLIFIER +2}; }
		if (n == 2 && isSetBonus) { args=new Object[]{(int) ((getRangedMultiplier()-1)*100)}; }
		else if (n == 1 && !isSetBonus) { args=new Object[]{DF.format(100*getWitherChance(slot)), WITHER_SECONDS, WITHER_AMPLIFIER +1}; }
		return super.getTooltip(n, slot, isSetBonus, args);
	}

	@Override
	public void attackEntity(Entity entity, List<EquipmentSlot> slots) {
		if (!(entity instanceof LivingEntity)) {return;}

		double chance = slots.stream().map(this::getWitherChance).mapToDouble(f -> f).sum();
		boolean isFullSet = slots.size() == 4;
		int amplifier = WITHER_AMPLIFIER + (isFullSet ? 1 : 0);
		int duration = Time.SECOND.of(WITHER_SECONDS - (isFullSet ? 1 : 0));
		if (rand.nextDouble() <= chance) {
			StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.WITHER, duration, amplifier, false, false);
			((LivingEntity) entity).addStatusEffect(effect);
		}
	}

	@Override
	public String getName() {
		return "withering";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 45;
	}

	@Override
	public int getEnchantability() {
		return 18;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
	}

	@Override
	public float getToughness() {
		return 4.0F;
	}

	@Override
	public float getKnockbackResistance() {
		return 0.25F;
	}

	@Override
	public int getSetBonuses() {
		return 2;
	}

	@Override
	public int getPieceBonuses() {
		return 1;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(RegisterItems.NECRO_DUST);
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
		return 1.5F;
	}
}
