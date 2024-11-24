package net.mqx.losttime.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class IceSpearProjectileEntityModel extends Model {
    private final ModelPart iceSpear;

    public IceSpearProjectileEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.iceSpear = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                "ice_spear",
                ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -22.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F))
                        .uv(28, 4).cuboid(0.0F, -26.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(20, 22)
                        .cuboid(-1.0F, -24.0F, 0.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(20, 25)
                        .cuboid(0.0F, -25.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(20, 11)
                        .cuboid(-1.0F, -4.0F, -1.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)).uv(28, 7)
                        .cuboid(0.0F, -4.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(28, 17)
                        .cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(8, 0)
                        .cuboid(0.0F, -23.0F, -2.0F, 1.0F, 15.0F, 1.0F, new Dilation(0.0F)).uv(0, 20)
                        .cuboid(-1.0F, -21.0F, -2.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)).uv(12, 15)
                        .cuboid(1.0F, -22.0F, -1.0F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F)).uv(16, 0)
                        .cuboid(1.0F, -23.0F, 0.0F, 1.0F, 12.0F, 1.0F, new Dilation(0.0F)).uv(12, 0)
                        .cuboid(0.0F, -24.0F, 1.0F, 1.0F, 14.0F, 1.0F, new Dilation(0.0F)).uv(8, 16)
                        .cuboid(-1.0F, -21.0F, 1.0F, 1.0F, 12.0F, 1.0F, new Dilation(0.0F)).uv(16, 13)
                        .cuboid(-2.0F, -22.0F, 0.0F, 1.0F, 11.0F, 1.0F, new Dilation(0.0F)).uv(20, 0)
                        .cuboid(-2.0F, -20.0F, -1.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)).uv(24, 25)
                        .cuboid(-3.0F, -19.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(24, 0)
                        .cuboid(-3.0F, -18.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(28, 14)
                        .cuboid(-2.0F, -17.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(26, 10)
                        .cuboid(-2.0F, -20.0F, 1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(24, 5)
                        .cuboid(-1.0F, -19.0F, 2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(20, 16)
                        .cuboid(0.0F, -21.0F, 2.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)).uv(26, 21)
                        .cuboid(1.0F, -20.0F, 1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(24, 16)
                        .cuboid(2.0F, -19.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(16, 25)
                        .cuboid(2.0F, -20.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(4, 20)
                        .cuboid(1.0F, -21.0F, -2.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(4, 27)
                        .cuboid(0.0F, -20.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(28, 0)
                        .cuboid(-1.0F, -19.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 23.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumer vertices,
            int light,
            int overlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        this.iceSpear.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
