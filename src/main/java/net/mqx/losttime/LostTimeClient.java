package net.mqx.losttime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mqx.losttime.client.render.entity.model.ModEntityModelLayers;
import net.mqx.losttime.client.render.entity.model.cosmetic.HaloCosmeticEntityModel;
import net.mqx.losttime.client.render.entity.model.projectile.IceSpearProjectileEntityModel;
import net.mqx.losttime.client.render.entity.renderer.HaloEntityRenderer;
import net.mqx.losttime.client.render.entity.renderer.IceSpearEntityRenderer;
import net.mqx.losttime.entity.ModEntityType;

public class LostTimeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityType.ICE_SPEAR, IceSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityType.HALO_COSMETIC, HaloEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.ICE_SPEAR_PROJECTILE, IceSpearProjectileEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.HALO_COSMETIC, HaloCosmeticEntityModel::getTexturedModelData);
    }
}
