package net.mqx.losttime.client.render.entity.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.client.render.entity.model.ModEntityModelLayers;
import net.mqx.losttime.client.render.entity.model.cosmetic.HaloCosmeticEntityModel;
import net.mqx.losttime.entity.cosmetic.HaloCosmeticEntity;

public class HaloEntityRenderer extends EntityRenderer<HaloCosmeticEntity> {
    private final HaloCosmeticEntityModel model;
    private static final Identifier TEXTURE = new Identifier(LostTime.MOD_ID, "textures/entity/cosmetic/halo.png");

    public HaloEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new HaloCosmeticEntityModel(ctx.getPart(ModEntityModelLayers.HALO_COSMETIC));
    }

    @Override
    public void render(
            HaloCosmeticEntity entity,
            float yaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light
    ) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
                MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(
                MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(HaloCosmeticEntity entity) {
        return TEXTURE;
    }
}
