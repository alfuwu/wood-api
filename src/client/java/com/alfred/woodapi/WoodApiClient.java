package com.alfred.woodapi;

import com.alfred.woodapi.registry.BlockEntities;
import com.alfred.woodapi.registry.EntityTypes;
import com.alfred.woodapi.render.entity.BoatEntityRenderer;
import com.alfred.woodapi.render.entity.ChestBoatEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;

public class WoodApiClient implements ClientModInitializer {
	@Override
	@SuppressWarnings("unchecked")
	public void onInitializeClient() {
		EntityRendererRegistry.register(EntityTypes.BOAT_ENTITY, BoatEntityRenderer::new);
		EntityRendererRegistry.register(EntityTypes.CHEST_BOAT_ENTITY, ChestBoatEntityRenderer::new);

		BlockEntityRendererRegistry.register((BlockEntityType<SignBlockEntity>) BlockEntities.SIGN, SignBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register((BlockEntityType<SignBlockEntity>) BlockEntities.HANGING_SIGN, HangingSignBlockEntityRenderer::new);
	}
}