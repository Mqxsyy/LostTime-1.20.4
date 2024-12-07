package net.mqx.losttime.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.mqx.losttime.util.Vec2;

public class CosmeticScreenManager {
    private static final Identifier COSMETIC_SLOT = Identifier.of("losttime", "textures/gui/cosmetic_slot.png");

    public static CosmeticScreen currentScreen;

    public static void init(CosmeticScreen screen) {
        currentScreen = screen;
    }

    public static void draw(DrawContext context) {
        CosmeticPlayerScreenHandler screen = currentScreen.cosmetics$getHandler();
        Vec2 slotPos = screen.cosmetics$getSlotPos();
        int x = currentScreen.cosmetics$getX();
        int y = currentScreen.cosmetics$getY();
        context.drawTexture(COSMETIC_SLOT, x + slotPos.x - 1, y + slotPos.y - 1, 0, 0, 18, 18);
    }
}
