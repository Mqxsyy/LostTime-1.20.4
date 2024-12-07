package net.mqx.losttime.item.cosmetic;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.mqx.losttime.entity.cosmetic.HaloCosmeticEntity;

public class HaloCosmeticItem extends CosmeticItem {
    private HaloCosmeticEntity haloCosmeticEntity;

    public HaloCosmeticItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void onEquip(PlayerEntity owner, World world) {
        if (world.isClient())
            return;

        haloCosmeticEntity = new HaloCosmeticEntity(owner.getWorld(), owner);
        owner.getWorld().spawnEntity(haloCosmeticEntity);
    }

    @Override
    public void onUnequip(PlayerEntity owner, World world) {
        if (world.isClient())
            return;

        haloCosmeticEntity.discard();
    }
}
