package net.mqx.losttime.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.client.render.entity.model.IceSpearProjectileEntityModel;
import net.mqx.losttime.client.render.entity.model.ModEntityModelLayers;
import net.mqx.losttime.entity.projectile.IceSpearProjectileEntity;

@Environment(EnvType.CLIENT)
public class IceSpearEntityRenderer extends EntityRenderer<IceSpearProjectileEntity> {
    private final IceSpearProjectileEntityModel model;
    private static final Identifier TEXTURE = new Identifier(LostTime.MOD_ID, "textures/entity/ice_spear.png");

    public IceSpearEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new IceSpearProjectileEntityModel(ctx.getPart(ModEntityModelLayers.ICE_SPEAR_PROJECTILE));
    }

    @Override
    public void render(
            IceSpearProjectileEntity entity,
            float yaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light
    ) {
        matrices.push();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(IceSpearProjectileEntity entity) {
        return TEXTURE;
    }
}
