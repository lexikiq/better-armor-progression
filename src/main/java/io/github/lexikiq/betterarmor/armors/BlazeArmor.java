package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;

import java.util.List;

@Getter
@NoArgsConstructor
public class BlazeArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{1, 4, 5, 2};
	private static final double FIRE_CHANCE = 0.005; // .5%

	@Override
	public String getName() {
		return "blaze";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 15;
	}

	@Override
	public int getEnchantability() {
		return 12;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
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
		return 2;
	}

	@Override
	public int getPieceBonuses() {
		return 1;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(Items.BLAZE_POWDER);
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
		return 1.1F;
	}

	private double getFireChance(EquipmentSlot slot) {return FIRE_CHANCE *(double)getIngredients(slot);}

	@Override
	public void armorTick(World world, Entity entity, int armorCount) {
		StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 8, 0, false, false);
		LivingEntity player = (LivingEntity) entity;
		player.addStatusEffect(effect);
	}

	@Override
	public void movementTick(World world, Entity entity) {
		world.addParticle(ParticleTypes.LARGE_SMOKE, entity.getParticleX(0.5D), entity.getRandomBodyY(), entity.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object... args) {
		if (n == 2 && isSetBonus) { args=new Object[]{(int) ((getRangedMultiplier()-1)*100)}; }
		else if (n == 1 && !isSetBonus) { args=new Object[]{DF.format(100*getFireChance(slot))}; }
		return super.getTooltip(n, slot, isSetBonus, args);
	}

	@Override
	public void attackEntity(Entity entity, List<EquipmentSlot> slots) {
		if (entity.isOnFire()) {return;}
		double chance = slots.stream().map(this::getFireChance).mapToDouble(f -> f).sum();
		if (rand.nextDouble() <= chance) {
			entity.setOnFireFor(5);
		}
	}
}
