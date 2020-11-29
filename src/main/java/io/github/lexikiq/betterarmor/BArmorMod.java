package io.github.lexikiq.betterarmor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.UUID;

public class BArmorMod implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "barmorprog";
    public static final String MOD_NAME = "Better Armor Progression";

    public static final ItemGroup BARMOR_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MOD_ID, "general"))
            .icon(() -> new ItemStack(RegisterItems.VOID_FRAGMENT))
            //.appendItems(stacks -> {
            //    stacks.add(new ItemStack(RegisterItems.END_FRAGMENT));
            //})
            .build();

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        RegisterItems.register(MOD_ID);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}