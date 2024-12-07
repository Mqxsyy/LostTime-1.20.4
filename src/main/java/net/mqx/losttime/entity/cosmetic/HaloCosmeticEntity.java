package net.mqx.losttime.entity.cosmetic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Ownable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.mqx.losttime.entity.ModEntityType;

import java.util.UUID;

public class HaloCosmeticEntity extends Entity implements Ownable {
    private UUID ownerUuid;
    private Entity owner;

    private int age = 0;

    public HaloCosmeticEntity(EntityType<HaloCosmeticEntity> entityType, World world) {
        super(entityType, world);
    }

    public HaloCosmeticEntity(World world, Entity owner) {
        super(ModEntityType.HALO_COSMETIC, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() + 0.5F, owner.getZ());
    }

    public void setOwner(Entity entity) {
        this.owner = entity;
        this.ownerUuid = entity.getUuid();
    }

    @Override
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld serverWorld) {
            this.owner = serverWorld.getEntity(this.ownerUuid);
            return this.owner;
        } else {
            return null;
        }
    }

    @Override
    protected void initDataTracker() {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    public void updateTrackedPositionAndAngles(
            double x,
            double y,
            double z,
            float yaw,
            float pitch,
            int interpolationSteps
    ) {} // existence of this empty function makes it smooth...? what???

    @Override
    public void tick() {
        super.tick();
        age++;

        if (this.owner == null)
            return;

        setPosition(getPos().lerp(
                new Vec3d(owner.getX(), owner.getEyeY() + 0.5F + Math.sin(age * 0.05F) * 0.05F, owner.getZ()), 0.5F));
        setRotation(age, 0.0F);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        Entity entity = this.getWorld().getEntityById(packet.getEntityData());
        if (entity != null) {
            this.setOwner(entity);
        }
    }
}
