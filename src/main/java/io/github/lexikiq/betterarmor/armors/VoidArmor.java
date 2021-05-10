package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import io.github.lexikiq.betterarmor.items.RegisterItems;
import io.github.lexikiq.betterarmor.utils.PlayerUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;

@Getter
@NoArgsConstructor
public class VoidArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{3, 6, 7, 2};

	final static double RANGE = 1.0;

	@Override
	public void armorTick(World world, Entity entity, int count) {
		PlayerUtils.setRange((LivingEntity) entity, RANGE);
	}

	@Override
	public void noArmorTick(World world, Entity entity) {
		PlayerUtils.setRange((LivingEntity) entity, null);
	}

	@Override
	public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object ... args) {
		if (n == 1 && isSetBonus) {
			args = new Object[]{RANGE};
		}
		return super.getTooltip(n, slot, isSetBonus, args);
	}

	@Override
	public String getName() {
		return "void";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 30;
	}

	@Override
	public int getEnchantability() {
		return 10;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
	}

	@Override
	public float getToughness() {
		return 0.5F;
	}

	@Override
	public float getKnockbackResistance() {
		return 0.0F;
	}

	@Override
	public int getSetBonuses() {
		return 2;
	}

	@Override
	public int getPieceBonuses() {
		return 0;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(RegisterItems.VOID_FRAGMENT);
	}

	@Override
	public boolean isPartialSet() {
		return false;
	}

	@Override
	public boolean noPearlDamage() {
		return true;
	}

	@Override
	public float getRangedMultiplier() {
		return 1.0F;
	}
}
