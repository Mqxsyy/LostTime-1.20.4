package net.mqx.losttime.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.entity.ModEntityType;
import net.mqx.losttime.interfaces.TimerAccess;
import net.mqx.losttime.util.LostTimeUtils;
import net.mqx.losttime.util.TimerData;

import java.util.ArrayList;
import java.util.List;

public class IceSpearProjectileEntity extends ProjectileEntity {
    private boolean inGround = false;
    public boolean isExploding = false;
    private int aliveTicks = 0;
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
        this.aliveTicks++;

        if (!inGround) {
            Vec3d vel = this.getVelocity();
            Vec3d pos = this.getPos();

            if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
                double d = vel.horizontalLength();
                this.setYaw((float) (MathHelper.atan2(vel.x, vel.z) * 180.0F / (float) Math.PI));
                this.setPitch((float) (MathHelper.atan2(vel.y, d) * 180.0F / (float) Math.PI));
                this.prevYaw = this.getYaw();
                this.prevPitch = this.getPitch();
            }

            double drag = 0.99F;
            double x = vel.x * drag;
            double y = vel.y * drag - 0.05F;
            double z = vel.z * drag;
            double horizontalLength = vel.horizontalLength();

            this.setVelocity(new Vec3d(x, y, z));

            HitResult hitResult = this.getWorld()
                    .raycast(new RaycastContext(
                            pos, pos.add(this.getVelocity()), RaycastContext.ShapeType.COLLIDER,
                            RaycastContext.FluidHandling.NONE, this
                    ));

            if (hitResult.getType() == HitResult.Type.MISS) {
                EntityHitResult entityHitResult = this.getEntityCollision(pos, pos.add(this.getVelocity()));
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }
            }

            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            } else {
                this.setYaw((float) (MathHelper.atan2(vel.x, vel.z) * 180.0F / (float) Math.PI));
                this.setPitch((float) (MathHelper.atan2(vel.y, horizontalLength) * 180.0F / (float) Math.PI));
                this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
                this.setYaw(updateRotation(this.prevYaw, this.getYaw()));

                this.setPosition(pos.add(this.getVelocity()));
            }

            if (this.aliveTicks % 4 == 0) {
                World world = this.getWorld();
                if (!world.isClient) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    serverWorld.spawnParticles(
                            ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z, 1, 0.1F, 0.1F, 0.1F, 0.02F);
                }
            }

            return;
        }

        this.age++;

        if (this.age > 60) {
            if (isExploding)
                return;

            World world = this.getWorld();

            if (!world.isClient()) {
                ServerWorld serverWorld = (ServerWorld) this.getWorld();
                Vec3d pos = getPos().subtract(getVelocity());

                serverWorld.playSound(
                        null, this.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 0.5F, 1.05F);
                serverWorld.spawnParticles(ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z, 3, 0.1F, 0.1F, 0.1F, 0.04F);
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
        this.setPosition(entityHitResult.getPos());

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
        world.spawnParticles(ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z, 10, 0.1F, 0.1F, 0.1F, 0.1F);
        world.spawnParticles(ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z, 25, 0.1F, 0.1F, 0.1F, 0.15F);

        ApplyEffects();
        AffectGround();
        this.remove(RemovalReason.DISCARDED);
    }

    private void ApplyEffects() {
        Box box = this.getBoundingBox().expand(4.0, 4.0, 4.0);
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

    private void AffectGround() {
        BlockPos pos = this.getBlockPos();
        World world = this.getWorld();

        ArrayList<BlockPos> affectedBlockPositions = new ArrayList<>();
        ArrayList<BlockState> affectedBlockStates = new ArrayList<>();

        int range = 3;

        Vec3d rayOrigin = this.getPos().subtract(this.getVelocity());

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos targetPos = pos.add(new BlockPos(x, y, z));
                    double distance = LostTimeUtils.GetDistance(pos, targetPos);
                    if (distance > range)
                        continue;

                    if (world.getBlockEntity(targetPos) != null)
                        continue;

                    BlockState blockState = world.getBlockState(targetPos);

                    if (blockState.isAir())
                        continue;

                    if (blockState.isOf(Blocks.PACKED_ICE) || blockState.isOf(Blocks.ICE) || blockState.isOf(
                            Blocks.SNOW_BLOCK) || blockState.isOf(Blocks.WATER))
                        continue;

                    if (blockState.isTransparent(world, targetPos))
                        continue;

                    float hardness = blockState.getHardness(world, targetPos);
                    if (hardness < 0 || hardness > 2.5)
                        return;

                    if (LostTimeUtils.random.nextLong(range) < Math.floor(distance))
                        continue;

                    Vec3d rayTarget = new Vec3d(
                            targetPos.getX() + 0.5F, targetPos.getY() + 0.5F, targetPos.getZ() + 0.5F);

                    HitResult hitResult = world.raycast(
                            new RaycastContext(
                                    rayOrigin, rayTarget, RaycastContext.ShapeType.OUTLINE,
                                    RaycastContext.FluidHandling.NONE, this
                            ));

                    Vec3d hitPos = hitResult.getPos();
                    double hitX = hitPos.x % 1 == 0 ? hitPos.x - 0.0001F : hitPos.x;
                    double hitY = hitPos.y % 1 == 0 ? hitPos.y - 0.0001F : hitPos.y;
                    double hitZ = hitPos.z % 1 == 0 ? hitPos.z - 0.0001F : hitPos.z;
                    Vec3d correctedHitPos = new Vec3d(hitX, hitY, hitZ);

                    if (correctedHitPos.distanceTo(rayTarget) > 1.0F)
                        continue;

                    affectedBlockPositions.add(targetPos);
                    affectedBlockStates.add(blockState);

                    long rng = LostTimeUtils.random.nextLong(6);
                    if (rng == 1) {
                        world.setBlockState(targetPos, Blocks.PACKED_ICE.getDefaultState());
                    } else if (rng <= 3) {
                        world.setBlockState(targetPos, Blocks.ICE.getDefaultState());
                    } else {
                        world.setBlockState(targetPos, Blocks.SNOW_BLOCK.getDefaultState());
                    }
                }
            }
        }

        for (int i = 0; i < affectedBlockPositions.size(); i++) {
            final int index = i;
            ((TimerAccess) world).createTimer(new TimerData(
                    LostTimeUtils.random.nextLong(40, 60), () -> {
                world.setBlockState(affectedBlockPositions.get(index), affectedBlockStates.get(index));
            }
            ));
        }
    }
}