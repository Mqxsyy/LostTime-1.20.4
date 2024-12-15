package net.mqx.losttime.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.entity.player.CosmeticInventory;
import net.mqx.losttime.interfaces.CosmeticInventoryAccess;
import net.mqx.losttime.screen.CosmeticPlayerScreenHandler;
import net.mqx.losttime.util.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeScreenHandlerMixin extends ScreenHandler implements CosmeticPlayerScreenHandler {
    private static final int COSMETIC_ID = 46;
    private final Vec2 slotPos = new Vec2(127, 20);

    private CreativeScreenHandlerMixin() {
        super(null, 0);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(PlayerEntity player, CallbackInfo ci) {
        CosmeticInventoryAccess cosmeticInventoryAccess = (CosmeticInventoryAccess) player;
        CosmeticInventory cosmeticInventory = cosmeticInventoryAccess.cosmetic$getCosmeticInventory();
        this.addSlot(new Slot(cosmeticInventory, COSMETIC_ID, slotPos.x, slotPos.y));
    }

    @Override
    public Vec2 cosmetics$getSlotPos() {
        return this.slotPos;
    }
}
