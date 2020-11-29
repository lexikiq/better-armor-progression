package io.github.lexikiq.betterarmor.utils;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;

public class PlayerUtils {
    private static final EntityAttribute ATTACK_ATTRIBUTE = ReachEntityAttributes.ATTACK_RANGE;
    private static final EntityAttribute REACH_ATTRIBUTE = ReachEntityAttributes.REACH;

    public static void setRange(LivingEntity entity, Double range) {
        boolean enable = range != null;
        double currentValue = entity.getAttributeBaseValue(ATTACK_ATTRIBUTE);
        double currentValue2 = entity.getAttributeBaseValue(REACH_ATTRIBUTE);
        if ((enable && (currentValue > 0.0 || currentValue2 > 0.0)) || (!enable && (currentValue == 0.0 || currentValue2 == 0.0))) {return;}
        double newValue = enable ? range : 0.0;
        entity.getAttributeInstance(ATTACK_ATTRIBUTE).setBaseValue(newValue);
        entity.getAttributeInstance(REACH_ATTRIBUTE).setBaseValue(newValue);
    }
}
