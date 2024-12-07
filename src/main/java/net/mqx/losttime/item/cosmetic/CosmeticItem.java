package net.mqx.losttime.item.cosmetic;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public abstract class CosmeticItem extends Item {
    public CosmeticItem(Settings settings) {
        super(settings);
    }

    public abstract void onEquip(LivingEntity owner, World world);

    public abstract void onUnequip(LivingEntity owner, World world);
}
