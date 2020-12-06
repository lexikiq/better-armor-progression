package io.github.lexikiq.betterarmor.entity;

import io.github.lexikiq.betterarmor.BArmorMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityType {
    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(BArmorMod.MOD_ID, id), type.build());
    }

    public static final EntityType<BeenadeEntity> BEENADE;

    static {
        BEENADE = register("beenade", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BeenadeEntity::new).dimensions(EntityDimensions.fixed(0.7F, 0.6F)).trackRangeBlocks(8).fireImmune());
    }
}
