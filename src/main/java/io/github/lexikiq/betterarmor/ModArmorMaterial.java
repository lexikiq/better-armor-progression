package io.github.lexikiq.betterarmor;

import io.github.lexikiq.betterarmor.access.PlayerEntityAccess;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public abstract class ModArmorMaterial implements ArmorMaterial {
	protected static DecimalFormat DF = new DecimalFormat("0.0");
	protected static int[] INGREDIENTS = new int[]{4, 7, 8, 5};
	protected static int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	protected static final Random rand = new Random();

	public static int getIngredients(EquipmentSlot slot) {
		return INGREDIENTS[slot.getEntitySlotId()];
	};

	@Override
	public final int getDurability(EquipmentSlot slot){
		return BASE_DURABILITY[slot.getEntitySlotId()] * getDurabilityMultiplier();
	}

	public final int getProtectionAmount(EquipmentSlot slot){
		return getProtectionAmounts()[slot.getEntitySlotId()];
	}

	public abstract float getRangedMultiplier();
	public abstract boolean isPartialSet();
	public abstract boolean noPearlDamage();
	public abstract int getDurabilityMultiplier();
	public abstract int getSetBonuses();
	public abstract int getPieceBonuses();
	public abstract int[] getProtectionAmounts();

	public void movementTick(World world, Entity entity) {};

	public void armorTick(World world, Entity entity, int count) {};

	public final void armorTick(World world, Entity entity) {armorTick(world, entity, 4);}

	public void noArmorTick(World world, Entity entity) {};

	public void attackEntity(Entity entity, List<EquipmentSlot> armorCount) {};

	public boolean useSpecial(PlayerEntity entity) {return false;};

	public boolean useMana(PlayerEntity playerEntity, int manaCost) {
		PlayerEntityAccess playerEntityAccess = (PlayerEntityAccess) playerEntity;
		if (playerEntityAccess.useMana(manaCost)) {
			return true;
		} else {
			BArmorMod.adventure().audience(playerEntity).sendActionBar(
					Component.translatable(BArmorMod.MOD_ID+".mana_cooldown")
							.args(
									Component.text(manaCost),
									Component.text(playerEntityAccess.getMana())
							));
			return false;
		}
	}

	public MutableText getTooltip(int n, EquipmentSlot slot, boolean isSetBonus, Object ... args) {
		String key = isSetBonus ? "set" : "piece";
		return new TranslatableText("item."+BArmorMod.MOD_ID+"."+getName().toLowerCase()+"."+key+n, args).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xb37fc9)));
	}

	public List<MutableText> getTooltips(EquipmentSlot slot) {
		List<MutableText> output = new ArrayList<>();
		if (getSetBonuses() > 0) {
			output.add(new TranslatableText("barmorprog.set").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x8e51a8)).withBold(true)));
			for (int i = 1; i <= getSetBonuses(); i++) {
				output.add(getTooltip(i, slot, true));
			}
		}
		if (getPieceBonuses() > 0) {
			output.add(new TranslatableText("barmorprog.piece").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x8e51a8)).withBold(true)));
			for (int i = 1; i <= getPieceBonuses(); i++) {
				output.add(getTooltip(i, slot, false));
			}
		}
		return output;
	}
}
