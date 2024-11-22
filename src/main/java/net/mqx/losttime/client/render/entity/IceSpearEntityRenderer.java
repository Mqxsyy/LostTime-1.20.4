package net.mqx.losttime.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mqx.losttime.entity.projectiles.IceSpearProjectileEntity;

public class IceSpearEntityRenderer extends EntityRenderer<IceSpearProjectileEntity> {
    protected IceSpearEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
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
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(IceSpearProjectileEntity entity) {
        return null;
    }
}
