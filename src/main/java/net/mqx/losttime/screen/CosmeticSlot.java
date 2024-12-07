package net.mqx.losttime.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.mqx.losttime.item.cosmetic.CosmeticItem;

public class CosmeticSlot extends Slot {
    private PlayerEntity owner;

    public CosmeticSlot(Inventory inventory, int index, int x, int y, PlayerEntity owner) {
        super(inventory, index, x, y);
        this.owner = owner;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof CosmeticItem;
    }

    @Override
    public void setStack(ItemStack stack, ItemStack previousStack) {
        super.setStackNoCallbacks(stack);

        if (stack != previousStack && previousStack != ItemStack.EMPTY) {
            CosmeticItem cosmeticItem = (CosmeticItem) previousStack.getItem();
            cosmeticItem.onUnequip(owner, owner.getWorld());
            return;
        }

        CosmeticItem cosmeticItem = (CosmeticItem) stack.getItem();
        cosmeticItem.onEquip(owner, owner.getWorld());
    }
}
