package io.github.lexikiq.betterarmor.entity;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public enum RegisterEntities {
    BEENADE(ModEntityType.BEENADE) {
        @Override
        public void registerRenderer() {
            EntityRendererRegistry.INSTANCE.register(ModEntityType.BEENADE, (dispatcher, context) -> new BeeEntityRenderer(dispatcher));
        }

        @Override
        public void registerAttributes() {
            FabricDefaultAttributeRegistry.register(ModEntityType.BEENADE, BeenadeEntity.createBeeAttributes());
        }
    };

    private final EntityType<? extends Entity> entityType;

    RegisterEntities(EntityType<? extends Entity> entityType) {
        this.entityType = entityType;
    }

    public abstract void registerRenderer();

    public abstract void registerAttributes();
}
