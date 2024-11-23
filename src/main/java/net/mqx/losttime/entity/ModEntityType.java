package net.mqx.losttime.entity;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.entity.projectile.IceSpearProjectileEntity;

public class ModEntityType implements ModInitializer {
    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(LostTime.MOD_ID, id), type.build(id));
    }

    public static final EntityType<IceSpearProjectileEntity> ICE_SPEAR = register(
            "ice_spear", EntityType.Builder.create(
                    IceSpearProjectileEntity::new, SpawnGroup.MISC).setDimensions(0.25f, 0.25f)
    );

    @Override
    public void onInitialize() {

    }
}
