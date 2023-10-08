package com.freechazz.game.pieces.stats;

public abstract class TickerBuff extends Buff{
    private int duration;
    private int tick;

    public TickerBuff(int duration) {
        this.duration = duration;
    }

    public void tick() {
        tick++;
    }

    public abstract void perform();

    public boolean isExpired() {
        return tick >= duration;
    }
}
