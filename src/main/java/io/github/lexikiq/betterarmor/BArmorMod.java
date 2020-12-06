package io.github.lexikiq.betterarmor;

import io.github.lexikiq.betterarmor.entity.RegisterEntities;
import io.github.lexikiq.betterarmor.items.ModArmorItem;
import io.github.lexikiq.betterarmor.items.RegisterArmorItems;
import io.github.lexikiq.betterarmor.items.RegisterItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BArmorMod implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "barmorprog";
    public static final String MOD_NAME = "Better Armor Progression";

    public static final ItemGroup BARMOR_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MOD_ID, "general"))
            .icon(() -> new ItemStack(RegisterItems.VOID_FRAGMENT.getItem()))
            //.appendItems(stacks -> {
            //    stacks.add(new ItemStack(RegisterItems.END_FRAGMENT.getItem()));
            //})
            .build();

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        // registries
        Arrays.stream(RegisterItems.values()).forEach(RegisterItems::register); // register normal items
        RegisterArmorItems.register(); // armor items

        // entity registries
        Arrays.stream(RegisterEntities.values()).forEach(RegisterEntities::registerAttributes);

        // attack listener
        AttackEntityCallback.EVENT.register(((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!world.isClient) {
                getModdedArmor(playerEntity).forEach((k, v) -> k.attackEntity(entity, v));
            }
            return ActionResult.PASS;
        }));

        final Random itemRandom = new Random();
        UseItemCallback.EVENT.register(((player, world, hand) -> {
            ItemStack item = hand == Hand.MAIN_HAND ? player.getMainHandStack() : player.getOffHandStack();
            if (hand == Hand.MAIN_HAND && !world.isClient) {
                if (item.getItem() instanceof HoeItem && player.isSneaking()) {
                    ModArmorMaterial mat = getPlayerMaterial(player);
                    if (mat != null) {
                        boolean used = mat.useSpecial(player);
                        if (used && !player.isCreative()) {
                            item.damage(1, itemRandom, (ServerPlayerEntity) player);
                        }
                    }
                }
            }
            return TypedActionResult.pass(item);
        }));
    }

    public static Map<ModArmorMaterial, List<EquipmentSlot>> getModdedArmor(LivingEntity livingEntity) {
        Map<ModArmorMaterial, List<EquipmentSlot>> armor = new HashMap<>();
        if (!(livingEntity instanceof PlayerEntity)) {return armor;}

        PlayerEntity playerEntity = (PlayerEntity) livingEntity;
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