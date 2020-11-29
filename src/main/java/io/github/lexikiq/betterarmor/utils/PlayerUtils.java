package io.github.lexikiq.betterarmor.utils;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;

public class PlayerUtils {
    private static final EntityAttribute attack_attribute = ReachEntityAttributes.ATTACK_RANGE;
    private static final EntityAttribute reach_attribute = ReachEntityAttributes.REACH;

    public static void setRange(LivingEntity entity, Double range) {
        boolean enable = range != null;
        double currentValue = entity.getAttributeBaseValue(attack_attribute);
        double currentValue2 = entity.getAttributeBaseValue(reach_attribute);
        if ((enable && (currentValue > 0.0 || currentValue2 > 0.0)) || (!enable && (currentValue == 0.0 || currentValue2 == 0.0))) {return;}
        double newValue = enable ? range : 0.0;
        entity.getAttributeInstance(attack_attribute).setBaseValue(newValue);
        entity.getAttributeInstance(reach_attribute).setBaseValue(newValue);
    }
}
