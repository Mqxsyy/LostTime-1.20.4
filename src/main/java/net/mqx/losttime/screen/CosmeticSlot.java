package net.mqx.losttime.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.mqx.losttime.item.cosmetic.CosmeticItem;

public class CosmeticSlot extends Slot {
    public CosmeticSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof CosmeticItem;
    }
}
