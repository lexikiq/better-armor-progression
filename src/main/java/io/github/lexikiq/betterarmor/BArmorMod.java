package io.github.lexikiq.betterarmor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // attack listener
        AttackEntityCallback.EVENT.register(((playerEntity, world, hand, entity, entityHitResult) -> {
            getModdedArmor(playerEntity).forEach((k, v) -> k.attackEntity(entity, v));
            return ActionResult.PASS;
        }));
    }

    public static Map<ModArmorMaterial, List<EquipmentSlot>> getModdedArmor(PlayerEntity playerEntity) {
        Map<ModArmorMaterial, List<EquipmentSlot>> armor = new HashMap<>();
        for (ItemStack stack : playerEntity.inventory.armor) {
            if (stack.getItem() instanceof ModArmorItem) {
                ModArmorItem item = (ModArmorItem) stack.getItem();
                ModArmorMaterial mat = item.getModdedMaterial();
                List<EquipmentSlot> equips = armor.getOrDefault(mat, new ArrayList<>());
                equips.add(item.getSlotType());
                armor.put(mat, equips);
            }
        }
        return armor;
    }

    @Nullable public static ModArmorMaterial getPlayerMaterial(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) {return null;}

        ModArmorMaterial mat = null;

        for (ItemStack piece : ((PlayerEntity) entity).inventory.armor) {
            if (!(piece.getItem() instanceof ModArmorItem)) {return null;}
            ModArmorItem item = (ModArmorItem) piece.getItem();
            ModArmorMaterial pieceMat = (ModArmorMaterial) item.getMaterial();
            if (mat == null) {mat = pieceMat;}
            else if (mat != pieceMat) {return null;}
        }

        return mat;
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
    public static void log(String message) {
        log(Level.INFO, message);
    }
}