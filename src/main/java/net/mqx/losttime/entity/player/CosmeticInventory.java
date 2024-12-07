package net.mqx.losttime.entity.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.item.cosmetic.CosmeticItem;

public class CosmeticInventory implements Inventory {
    protected ItemStack itemStack = ItemStack.EMPTY;

    public NbtList writeNbt(NbtList nbtList) {
        if (this.isEmpty())
            return nbtList;

        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putByte("CosmeticSlot", (byte) 0);
        this.getStack(0).writeNbt(nbtCompound);
        nbtList.add(nbtCompound);

        return nbtList;
    }

    public void readNbt(NbtList nbtList) {
        this.clear();

        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);

            if (!itemStack.isEmpty()) {
                this.setStack(0, itemStack);
            }
        }
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return this.removeStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (!itemStack.isEmpty()) {
            ItemStack stack = this.itemStack;
            this.itemStack = ItemStack.EMPTY;
            return stack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (stack.getItem() instanceof CosmeticItem) {
            this.itemStack = stack;
        }
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.itemStack = ItemStack.EMPTY;
    }
}
