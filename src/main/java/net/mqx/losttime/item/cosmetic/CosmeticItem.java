package net.mqx.losttime.item.cosmetic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public abstract class CosmeticItem extends Item {
    public CosmeticItem(Settings settings) {
        super(settings);
    }

    public abstract void onEquip(PlayerEntity owner, World world);

    public abstract void onUnequip(PlayerEntity owner, World world);
}
