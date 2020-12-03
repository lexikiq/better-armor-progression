package io.github.lexikiq.betterarmor.mixin;

import io.github.lexikiq.betterarmor.BArmorMod;
import io.github.lexikiq.betterarmor.ModArmorMaterial;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownEntity {
    protected EnderPearlEntityMixin(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void onCollisionApplyDamage(CallbackInfo ci) {
        ServerPlayerEntity owner = (ServerPlayerEntity) this.getOwner();
        ModArmorMaterial mat = BArmorMod.getPlayerMaterial(owner);
        if (mat != null && mat.blocksPearlDamage()) {
            this.remove();
            ci.cancel();
        }
    }
}
