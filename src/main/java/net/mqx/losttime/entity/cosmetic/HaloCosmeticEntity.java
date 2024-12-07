package net.mqx.losttime.entity.cosmetic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.mqx.losttime.entity.ModEntityType;

public class HaloCosmeticEntity extends CosmeticEntity {
    private int age = 0;

    public HaloCosmeticEntity(EntityType<HaloCosmeticEntity> entityType, World world) {
        super(entityType, world);
    }

    public HaloCosmeticEntity(World world, Entity owner) {
        super(ModEntityType.HALO_COSMETIC, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() + 0.5F, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        age++;

        Entity owner = this.getOwner();
        if (owner == null) {
            this.discard();
            return;
        }

        double x = owner.getX();
        double y = owner.getEyeY() + 0.5F + Math.sin(age * 0.05F) * 0.05F;
        double z = owner.getZ();

        this.setPosition(this.getPos().lerp(new Vec3d(x, y, z), 0.5F));
        this.setRotation(age, 0.0F);
    }
}
