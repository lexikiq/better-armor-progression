package io.github.lexikiq.betterarmor;

import io.github.lexikiq.betterarmor.utils.PlayerUtils;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Lazy;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
	VOID("void", 30, new int[]{3, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0.0F, 0.0D, 1, 0, RegisterItems.VOID_FRAGMENT){
		final double range = 1.0;

		@Override
		public void armorTick(World world, Entity entity) {
			PlayerUtils.setRange((LivingEntity) entity, range);
		}

		@Override
		public void noArmorTick(World world, Entity entity) {
			PlayerUtils.setRange((LivingEntity) entity, null);
		}

		@Override
		public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object ... args) {
			if (n == 1 && isSetBonus) {
				args = new Object[]{range};
			}
			return super.getTooltip(n, slot, isSetBonus, args);
		}


	},

	BLAZE("blaze", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, 0.1D, 2, 1, Items.BLAZE_POWDER){
		private final double fireChance = .5/100;
		private final Random rand = new Random();

		private double getFireChance(EquipmentSlot slot) {return fireChance*(double)getIngredients(slot);}

		@Override
		public void armorTick(World world, Entity entity) {
			StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 8, 0, true, false);
			LivingEntity player = (LivingEntity) entity;
			player.addStatusEffect(effect);
		}

		@Override
		public void movementTick(World world, Entity entity) {
			world.addParticle(ParticleTypes.LARGE_SMOKE, entity.getParticleX(0.5D), entity.getRandomBodyY(), entity.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
		}

		@Override
		public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object... args) {
			if (n == 2 && isSetBonus) { args=new Object[]{(int) ((rangedMultiplier-1)*100)}; }
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
	};

	protected static final int[] INGREDIENTS = new int[]{4, 7, 8, 5};
	protected static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	private static final DecimalFormat DF = new DecimalFormat("0.0");
	protected final @Getter @Environment(EnvType.CLIENT) String name;
	protected final int durabilityMultiplier;
	protected final int[] protectionAmounts;
	protected final @Getter int enchantability;
	protected final @Getter SoundEvent equipSound;
	protected final @Getter float toughness;
	protected final @Getter float knockbackResistance;
	protected final @Getter double rangedMultiplier;
	protected final int setBonuses;
	protected final int pieceBonuses;
	protected final Lazy<Ingredient> repairIngredientSupplier;

	ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, double rangedMultiplier, int bonuses, int tooltips, Supplier<Ingredient> supplier)
	{
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.rangedMultiplier = 1.0D + rangedMultiplier;
		this.setBonuses = bonuses;
		this.pieceBonuses = tooltips;
		this.repairIngredientSupplier = new Lazy<>(supplier);
	}

	ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, double rangedMultiplier, int bonuses, int tooltips, Item repairItem){
		this(name, durabilityMultiplier, protectionAmounts, enchantability, equipSound, toughness, knockbackResistance, rangedMultiplier, bonuses, tooltips, () -> Ingredient.ofItems(repairItem));
	}

	 public int getIngredients(EquipmentSlot slot) {
		return INGREDIENTS[slot.getEntitySlotId()];
	}

	public int getDurability(EquipmentSlot slot){
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}

	public int getProtectionAmount(EquipmentSlot slot){
		return this.protectionAmounts[slot.getEntitySlotId()];
	}

	public Ingredient getRepairIngredient(){
		return this.repairIngredientSupplier.get();
	}

	public void movementTick(World world, Entity entity) {}

	public void armorTick(World world, Entity entity) {}

	public void noArmorTick(World world, Entity entity) {}

	public void attackEntity(Entity entity, List<EquipmentSlot> armorCount) {}

	public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object ... args) {
		String key = isSetBonus ? "set" : "piece";
		return new TranslatableText("item."+BArmorMod.MOD_ID+"."+this.name.toLowerCase()+"."+key+n, args).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xb37fc9)));
	}

	public List<MutableText> getTooltips(EquipmentSlot slot) {
		List<MutableText> output = new ArrayList<>();
		if (setBonuses > 0) {
			output.add(new TranslatableText("barmorprog.set").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x8e51a8)).withBold(true)));
			for (int i = 1; i <= setBonuses; i++) {
				output.add(getTooltip(i, slot, true));
			}
		}
		if (pieceBonuses > 0) {
			output.add(new TranslatableText("barmorprog.piece").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x8e51a8)).withBold(true)));
			for (int i = 1; i <= pieceBonuses; i++) {
				output.add(getTooltip(i, slot, false));
			}
		}
		return output;
	}
}