package io.github.lexikiq.betterarmor;

import io.github.lexikiq.betterarmor.entity.RegisterEntities;
import net.fabricmc.api.ClientModInitializer;

import java.util.Arrays;

public class BArmorModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // entity renderer registry
        Arrays.stream(RegisterEntities.values()).forEach(RegisterEntities::registerRenderer);
    }
}
