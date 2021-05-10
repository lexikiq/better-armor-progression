package io.github.lexikiq.betterarmor.items;

import io.github.lexikiq.betterarmor.ModArmorMaterial;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModArmorItem extends ArmorItem {
    public ModArmorItem(ModArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.addAll(((ModArmorMaterial) type).getTooltips(slot));
    }

    @Override
    public ModArmorMaterial getMaterial() {
        return (ModArmorMaterial) super.getMaterial();
    }
}
