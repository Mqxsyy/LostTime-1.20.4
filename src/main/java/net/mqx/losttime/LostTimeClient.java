package net.mqx.losttime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mqx.losttime.client.render.entity.IceSpearEntityRenderer;
import net.mqx.losttime.client.render.entity.model.IceSpearProjectileEntityModel;
import net.mqx.losttime.client.render.entity.model.ModEntityModelLayers;
import net.mqx.losttime.entity.ModEntityType;

@Environment(EnvType.CLIENT)
public class LostTimeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityType.ICE_SPEAR, IceSpearEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.ICE_SPEAR_PROJECTILE, IceSpearProjectileEntityModel::getTexturedModelData);
    }
}
