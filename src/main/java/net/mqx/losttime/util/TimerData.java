package net.mqx.losttime.util;

public class TimerData {
    private long ticksLeft;
    private final Runnable callback;

    public TimerData(long ticksLeft, Runnable callback) {
        this.ticksLeft = ticksLeft;
        this.callback = callback;
    }

    public boolean tick() {
        if (this.ticksLeft == 0L) {
            this.callback.run();
            return true;
        }

        this.ticksLeft--;
        return false;
    }
}
