package net.mqx.losttime.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.mqx.losttime.entity.player.CosmeticInventory;
import net.mqx.losttime.interfaces.CosmeticInventoryAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements CosmeticInventoryAccess {
    private final CosmeticInventory cosmeticInventory = new CosmeticInventory();

    protected PlayerEntityMixin() {
        super(null, null);
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtList nbtList = nbt.getList("CosmeticInventory", NbtElement.COMPOUND_TYPE);
        this.cosmeticInventory.readNbt(nbtList);
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("CosmeticInventory", this.cosmeticInventory.writeNbt(new NbtList()));
    }

    public CosmeticInventory cosmetic$getCosmeticInventory() {
        return this.cosmeticInventory;
    }
}