package io.github.lexikiq.betterarmor.utils;

public enum Time {
    SECOND(20),
    MINUTE(20*60),
    HOUR(20*60*60),
    DAY(20*60*60*24);

    private final int ticks;
    Time(int ticks) {
        this.ticks = ticks;
    }
    public int of(double multiplier) {
        return (int) (ticks * multiplier);
    }
}
