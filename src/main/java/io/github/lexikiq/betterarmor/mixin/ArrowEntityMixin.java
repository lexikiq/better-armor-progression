package io.github.lexikiq.betterarmor.mixin;

import io.github.lexikiq.betterarmor.BArmorMod;
import io.github.lexikiq.betterarmor.ModArmorMaterial;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity {

    // do i need these?? i dunno!!
    protected ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method="<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)V")
    protected void init(World world, LivingEntity owner, CallbackInfo ci) {
        if (!(owner instanceof PlayerEntity)) {return;}
        ModArmorMaterial mat = BArmorMod.getPlayerMaterial(owner);
        if (mat != null) {
            this.setDamage(this.getDamage()*mat.getRangedMultiplier());
        }
    }

    @Inject(at = @At("HEAD"), method = "onHit")
    protected void onHit(LivingEntity target, CallbackInfo ci) {
        if (!(this.getOwner() instanceof PlayerEntity)) {return;}
        PlayerEntity owner = (PlayerEntity) this.getOwner();
        BArmorMod.getModdedArmor(owner).forEach((mat, count) -> mat.attackEntity(target, count));
    }
}
