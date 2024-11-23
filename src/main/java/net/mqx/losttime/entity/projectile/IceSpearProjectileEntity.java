package net.mqx.losttime.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import net.mqx.losttime.entity.ModEntityType;

public class IceSpearProjectileEntity extends ProjectileEntity {
    public IceSpearProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceSpearProjectileEntity(World world, LivingEntity owner) {
        super(ModEntityType.ICE_SPEAR, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
    }

    @Override
    protected void initDataTracker() {

    }
}
