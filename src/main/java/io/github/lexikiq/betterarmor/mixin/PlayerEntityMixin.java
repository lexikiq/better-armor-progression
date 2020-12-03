package io.github.lexikiq.betterarmor.mixin;

import io.github.lexikiq.betterarmor.BArmorMod;
import io.github.lexikiq.betterarmor.ModArmorMaterial;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    private ModArmorMaterial armorSet = null;
    private Set<ModArmorMaterial> prevMats = new HashSet<>();
    private Vec3d lastPos = this.getPos();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        Map<ModArmorMaterial, List<EquipmentSlot>> allMat = BArmorMod.getModdedArmor(this);
        ModArmorMaterial newMat = null;

        // run partial ticks and get full set
        for (Map.Entry mapElement : allMat.entrySet()) {
            ModArmorMaterial localMat = (ModArmorMaterial) mapElement.getKey();
            List<EquipmentSlot> localVals = (List<EquipmentSlot>) mapElement.getValue();
            if (localVals.size() == 4) {
                newMat = (ModArmorMaterial) mapElement.getKey();
            } else if (localMat.allowsPartialSet()) {
                localMat.armorTick(this.world, this, localVals.size());
            }
        }

        // run noArmorTicks for partial sets
        Set<ModArmorMaterial> matList = allMat.keySet();
        prevMats.stream().filter(x -> !matList.contains(x) && x.allowsPartialSet()).forEach(x -> x.noArmorTick(this.world, this));
        prevMats = matList;

        // process full sets
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