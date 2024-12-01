package net.mqx.losttime.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.Vec2f;
import net.mqx.losttime.screen.CosmeticPlayerScreenHandler;
import net.mqx.losttime.util.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler implements CosmeticPlayerScreenHandler {
    private static final int COSMETIC_ID = 46;
    private final Vec2 slotPos = new Vec2(77, 44);

    @Unique
    private PlayerInventory inventory;

    private PlayerScreenHandlerMixin(PlayerInventory inventory) {
        super(null, 0);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(PlayerInventory playerInv, boolean onServer, PlayerEntity owner, CallbackInfo info) {
        this.inventory = playerInv;
        this.addSlot(new Slot(inventory, COSMETIC_ID, slotPos.x, slotPos.y));
    }

    @Override
    public Vec2 cosmetics$getSlotPos() {
        return this.slotPos;
    }
}