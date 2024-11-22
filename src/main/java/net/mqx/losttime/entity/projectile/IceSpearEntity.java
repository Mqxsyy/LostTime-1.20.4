package net.mqx.losttime.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class IceSpearProjectileEntity extends ProjectileEntity {
    public IceSpearProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }



    @Override
    protected void initDataTracker() {

    }
}
