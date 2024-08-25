package com.alfred.woodapi.render.entity;

import com.alfred.woodapi.entity.BoatEntity;
import com.alfred.woodapi.registry.EntityTypes;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import java.util.Map;

public class BoatEntityRenderer extends EntityRenderer<BoatEntity> {
    private final Map<BoatEntity.BoatType, Pair<Identifier, CompositeEntityModel<BoatEntity>>> texturesAndModels;
    private static final net.minecraft.entity.vehicle.BoatEntity.Type DEFAULT_TYPE = net.minecraft.entity.vehicle.BoatEntity.Type.OAK;

    public BoatEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.texturesAndModels = EntityTypes.BOAT_TYPES.stream().collect(ImmutableMap.toImmutableMap(type -> type, type ->
                Pair.of(getTexture(type), this.createModel(ctx, type))));
    }

    @SuppressWarnings("unchecked")
    protected CompositeEntityModel<BoatEntity> createModel(EntityRendererFactory.Context ctx, BoatEntity.BoatType type) {
        EntityModelLayer entityModelLayer = EntityModelLayers.createBoat(DEFAULT_TYPE);
        ModelPart modelPart = ctx.getPart(entityModelLayer);
        if (type.isRaftLike())
            return (CompositeEntityModel) new RaftEntityModel(modelPart);
        else
            return (CompositeEntityModel) new BoatEntityModel(modelPart);
    }

    @Override
    public void render(BoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0f, 0.375f, 0.0f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - f));
        float h = (float) boatEntity.getDamageWobbleTicks() - g;
        float j = boatEntity.getDamageWobbleStrength() - g;
        if (j < 0.0f)
            j = 0.0f;

        if (h > 0.0f)
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(h) * h * j / 10.0f * boatEntity.getDamageWobbleSide()));

        float k = boatEntity.interpolateBubbleWobble(g);
        if (!MathHelper.approximatelyEquals(k, 0.0f))
            matrixStack.multiply((new Quaternionf()).setAngleAxis(boatEntity.interpolateBubbleWobble(g) * MathHelper.RADIANS_PER_DEGREE, 1.0f, 0.0f, 1.0f));

        Pair<Identifier, CompositeEntityModel<BoatEntity>> pair = this.texturesAndModels.get(boatEntity.getBoatType());
        Identifier identifier = pair.getFirst();
        CompositeEntityModel<BoatEntity> compositeEntityModel = pair.getSecond();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        compositeEntityModel.setAngles(boatEntity, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(compositeEntityModel.getLayer(identifier));
        compositeEntityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!boatEntity.isSubmergedInWater())
            if (compositeEntityModel instanceof ModelWithWaterPatch modelWithWaterPatch)
                modelWithWaterPatch.getWaterPatch().render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask()), i, OverlayTexture.DEFAULT_UV);

        matrixStack.pop();
        super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static Identifier getTexture(BoatEntity.BoatType type) {
        return type.getId().withPrefixedPath("textures/entity/boat/").withSuffixedPath(".png");
    }

    @Override
    public Identifier getTexture(BoatEntity entity) {
        return getTexture(entity.getBoatType());
    }
}
