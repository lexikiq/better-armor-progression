package io.github.lexikiq.betterarmor.armors;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import io.github.lexikiq.betterarmor.entity.BeenadeEntity;
import io.github.lexikiq.betterarmor.entity.ModEntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Getter
@NoArgsConstructor
public class BeeArmor extends ModArmorMaterial {
	private final int[] protectionAmounts = new int[]{1, 3, 4, 2};
	private final static int MANA_COST = 20;

	@Override
	public boolean useSpecial(PlayerEntity playerEntity) {
		if (useMana(playerEntity, MANA_COST)) {
			BeenadeEntity beenade = new BeenadeEntity(ModEntityType.BEENADE, playerEntity.world);
			beenade.initialize(((ServerWorld) playerEntity.world), playerEntity.world.getLocalDifficulty(playerEntity.getBlockPos()), SpawnReason.EVENT, null, null);
			beenade.initialize(playerEntity);
			playerEntity.world.spawnEntity(beenade);
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "bee";
	}

	@Override
	public int getDurabilityMultiplier() {
		return 12;
	}

	@Override
	public int getEnchantability() {
		return 10;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
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
		return 0;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(Items.HONEYCOMB);
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
