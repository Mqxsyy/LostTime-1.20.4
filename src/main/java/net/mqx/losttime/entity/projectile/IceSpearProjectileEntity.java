package net.mqx.losttime.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.mqx.losttime.entity.ModEntityType;

public class IceSpearProjectileEntity extends ProjectileEntity {
    private boolean inGround = false;
    private int age = 0;

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

    @Override
    public void tick() {
        super.tick();

        if (!inGround) {
            Vec3d velocity = this.getVelocity();
            Vec3d position = this.getPos();

            if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
                double d = velocity.horizontalLength();
                this.setYaw((float) (MathHelper.atan2(velocity.x, velocity.z) * 180.0F / (float) Math.PI));
                this.setPitch((float) (MathHelper.atan2(velocity.y, d) * 180.0F / (float) Math.PI));
                this.prevYaw = this.getYaw();
                this.prevPitch = this.getPitch();
            }

            double x = velocity.x * 0.975F;
            double y = velocity.y - 0.025F;
            double z = velocity.z * 0.975F;
            double horizontalLength = velocity.horizontalLength();

            this.setVelocity(new Vec3d(x, y, z));

            HitResult hitResult = this.getWorld().raycast(new RaycastContext(
                    position, position.add(this.getVelocity()), RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE, this
            ));

            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            } else {
                this.setYaw((float) (MathHelper.atan2(velocity.x, velocity.z) * 180.0F / (float) Math.PI));
                this.setPitch((float) (MathHelper.atan2(velocity.y, horizontalLength) * 180.0F / (float) Math.PI));
                this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
                this.setYaw(updateRotation(this.prevYaw, this.getYaw()));

                this.setPosition(position.add(this.getVelocity()));
            }

            return;
        }

        this.age++;

        if (this.age > 300) {
            this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.inGround = true;

        Vec3d offset = blockHitResult.getPos().subtract(this.getPos()).normalize();
        this.setVelocity(offset);
        this.setPosition(blockHitResult.getPos().add(offset.multiply(0.25F)));
    }

    public void Explode() {
        this.remove(RemovalReason.DISCARDED);
    }
}
