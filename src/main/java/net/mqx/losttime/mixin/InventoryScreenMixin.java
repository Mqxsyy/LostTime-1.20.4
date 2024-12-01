package net.mqx.losttime.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.PlayerScreenHandler;
import net.mqx.losttime.screen.CosmeticPlayerScreenHandler;
import net.mqx.losttime.screen.CosmeticScreen;
import net.mqx.losttime.screen.CosmeticScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements CosmeticScreen {
    private InventoryScreenMixin() {
        super(null, null, null);
    }

    @Inject(at = @At("HEAD"), method = "init")
    private void init(CallbackInfo info) {
        CosmeticScreenManager.init(this);
    }

    @Inject(at = @At("RETURN"), method = "drawBackground")
    private void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo info) {
        CosmeticScreenManager.draw(context);
    }

    @Override
    public CosmeticPlayerScreenHandler cosmetics$getHandler() {
        return (CosmeticPlayerScreenHandler) this.handler;
    }

    @Override
    public int cosmetics$getX() {
        return this.x;
    }

    @Override
    public int cosmetics$getY() {
        return this.y;
    }
}
