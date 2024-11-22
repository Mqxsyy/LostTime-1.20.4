package net.mqx.losttime.entity;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.entity.projectile.IceSpearProjectileEntity;

public class ModEntities implements ModInitializer {
    public static final EntityType<IceSpearProjectileEntity> ICE_SPEAR_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(LostTime.MOD_ID, "ice_spear_projectile"),
            EntityType.Builder.<IceSpearProjectileEntity>create(IceSpearProjectileEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.25f, 0.25f).build()
    );

    @Override
    public void onInitialize() {

    }
}
