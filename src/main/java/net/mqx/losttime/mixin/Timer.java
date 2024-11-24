package net.mqx.losttime.mixin;

import net.minecraft.server.world.ServerWorld;
import net.mqx.losttime.interfaces.TimerAccess;
import net.mqx.losttime.util.TimerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ServerWorld.class)
public abstract class Timer implements TimerAccess {
    @Unique
    ArrayList<TimerData> timerDataArrayList = new ArrayList<>();

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        for (int i = timerDataArrayList.size() - 1; i >= 0; i--) {
            TimerData timerData = timerDataArrayList.get(i);
            boolean executed = timerData.tick();

            if (executed) {
                timerDataArrayList.remove(i);
            }
        }
    }

    @Override
    public void createTimer(TimerData timerData) {
        timerDataArrayList.add(timerData);
    }
}