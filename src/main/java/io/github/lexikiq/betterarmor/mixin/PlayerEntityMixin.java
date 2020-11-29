package io.github.lexikiq.betterarmor.mixin;

import io.github.lexikiq.betterarmor.ModArmorItem;
import io.github.lexikiq.betterarmor.ModArmorMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity
{
    @Shadow @Final public PlayerInventory inventory;
    public ModArmorMaterial armorSet = null;

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        ModArmorMaterial newMat = getArmorMaterial();
        if (newMat != armorSet) {
            if (armorSet != null) {armorSet.noArmorTick(this.world, this);}
            armorSet = newMat;
        }
        if (armorSet != null) {
            armorSet.armorTick(this.world, this);
        }
    }

    private @Nullable ModArmorMaterial getArmorMaterial() {
        ModArmorMaterial mat = null;
        for (ItemStack piece : this.inventory.armor) {
            if (!(piece.getItem() instanceof ModArmorItem)) {return null;}
            ModArmorItem item = (ModArmorItem) piece.getItem();
            ModArmorMaterial pieceMat = (ModArmorMaterial) item.getMaterial();
            if (mat == null) {mat = pieceMat;}
            else if (mat != pieceMat) {return null;}
        }
        return mat;
    }
}