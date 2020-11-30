package io.github.lexikiq.betterarmor.mixin;

import io.github.lexikiq.betterarmor.BArmorMod;
import io.github.lexikiq.betterarmor.ModArmorMaterial;
import lombok.Getter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    private ModArmorMaterial armorSet = null;
    private Vec3d lastPos = this.getPos();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        ModArmorMaterial newMat = BArmorMod.getPlayerMaterial(this);
        if (newMat != armorSet) {
            if (armorSet != null) {armorSet.noArmorTick(this.world, this);}
            armorSet = newMat;
        }
        Vec3d currentPos = this.getPos();
        if (armorSet != null) {
            armorSet.armorTick(this.world, this);
            if (lastPos != currentPos && !this.isSneaky()) {
                armorSet.movementTick(this.world, this);
            }
        }
        lastPos = currentPos;
    }
}