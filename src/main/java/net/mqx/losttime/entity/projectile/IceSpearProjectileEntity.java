package net.mqx.losttime.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.mqx.losttime.entity.ModEntityType;

import java.util.List;

public class IceSpearProjectileEntity extends ProjectileEntity {
    private boolean inGround = false;
    public boolean isExploding = false;
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

            HitResult hitResult = this.getWorld()
                    .raycast(new RaycastContext(
                            position, position.add(this.getVelocity()), RaycastContext.ShapeType.COLLIDER,
                            RaycastContext.FluidHandling.NONE, this
                    ));

            if (hitResult.getType() == HitResult.Type.MISS) {
                EntityHitResult entityHitResult = this.getEntityCollision(position, position.add(this.getVelocity()));
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }
            }

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

        if (this.age > 60) {
            if (isExploding)
                return;

            World world = this.getWorld();

            if (!world.isClient()) {
                ServerWorld serverWorld = (ServerWorld) getWorld();
                Vec3d pos = getPos().subtract(getVelocity());

                serverWorld.playSound(
                        null, this.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 0.5F, 1.05F);
                serverWorld.spawnParticles(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 3, 0.1F, 0.1F, 0.1F, 0.02F);
                serverWorld.spawnParticles(ParticleTypes.WHITE_SMOKE, pos.x, pos.y, pos.z, 10, 0.1F, 0.1F, 0.1F, 0.04F);
            }

            this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return ProjectileUtil.getEntityCollision(
                this.getWorld(), this, currentPosition, nextPosition,
                this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::canHit
        );
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.inGround = true;

        Vec3d offset = blockHitResult.getPos().subtract(this.getPos()).normalize();
        this.setVelocity(offset);
        this.setPosition(blockHitResult.getPos().add(offset.multiply(0.25F)));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!this.getWorld().isClient) {
            Explode();
        }
    }

    public void Explode() {
        if (isExploding)
            return;

        isExploding = true;

        ServerWorld world = (ServerWorld) getWorld();
        Vec3d pos = getPos().subtract(getVelocity());

        world.playSound(null, this.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 1F, 1.05F);
        world.spawnParticles(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 5, 0.1F, 0.1F, 0.1F, 0.05F);
        world.spawnParticles(ParticleTypes.WHITE_SMOKE, pos.x, pos.y, pos.z, 25, 0.1F, 0.1F, 0.1F, 0.15F);

        ApplyEffects();
        this.remove(RemovalReason.DISCARDED);
    }

    private void ApplyEffects() {
        Box box = this.getBoundingBox().expand(5.0, 5.0, 5.0);
        List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            for (LivingEntity livingEntity : list) {
                double distance = this.distanceTo(livingEntity);
                double val = distance / 4.0;
                double percentage = 1 + 0.5 * (Math.cos(val * Math.PI) - 1);
                livingEntity.setFrozenTicks(Math.min(livingEntity.getFrozenTicks() + (int) (400 * percentage), 600));
                livingEntity.damage(this.getDamageSources().freeze(), (float) (8.0F * percentage));
            }
        }
    }
}
