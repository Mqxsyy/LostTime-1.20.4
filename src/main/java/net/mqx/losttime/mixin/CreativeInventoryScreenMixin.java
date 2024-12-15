package net.mqx.losttime.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.slot.Slot;
import net.mqx.losttime.interfaces.CosmeticInventoryAccess;
import net.mqx.losttime.screen.CosmeticPlayerScreenHandler;
import net.mqx.losttime.screen.CosmeticScreen;
import net.mqx.losttime.screen.CosmeticScreenManager;
import net.mqx.losttime.util.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> implements CosmeticScreen {
    private static final int COSMETIC_ID = 46;
    private final Vec2 slotPos = new Vec2(127, 20);

    @Shadow
    private static ItemGroup selectedTab;

    public CreativeInventoryScreenMixin() {
        super(null, null, null);
    }

    @Inject(at = @At("RETURN"), method = "setSelectedTab")
    private void setSelectedTab(ItemGroup group, CallbackInfo ci) {
        if (selectedTab.getType() != ItemGroup.Type.INVENTORY)
            return;

        MinecraftClient minecraftClient = this.client;
        if (this.client == null)
            return;

        CosmeticInventoryAccess cosmeticInventoryAccess = (CosmeticInventoryAccess) minecraftClient.player;
        if (cosmeticInventoryAccess == null)
            return;

//        this.handler.slots.add(new CreativeInventoryScreen.CreativeSlot(
//                new Slot(cosmeticInventoryAccess.cosmetic$getCosmeticInventory(), COSMETIC_ID, slotPos.x, slotPos.y),
//                COSMETIC_ID, slotPos.x, slotPos.y
//        ));
    }

    @Inject(at = @At("HEAD"), method = "init")
    private void init(CallbackInfo ci) {
        CosmeticScreenManager.init(this);
    }

    @Inject(at = @At("RETURN"), method = "drawBackground")
    private void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (selectedTab.getType() != ItemGroup.Type.INVENTORY)
            return;

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
