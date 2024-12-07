package net.mqx.losttime.client.render.entity.model.cosmetic;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class HaloCosmeticEntityModel extends Model {
    private final ModelPart root;

    public HaloCosmeticEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                "root",
                ModelPartBuilder.create().uv(18, 10).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-3.0F, -1.0F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).uv(0, 18)
                        .cuboid(-4.0F, -1.0F, -7.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).uv(0, 9)
                        .cuboid(2.0F, -1.0F, -8.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).uv(18, 0)
                        .cuboid(3.0F, -1.0F, -7.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).uv(18, 7)
                        .cuboid(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 1.0F, 4.0F)
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
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}