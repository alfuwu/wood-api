package com.alfred.woodapi;

import com.alfred.woodapi.registry.Wood;
import com.alfred.woodapi.render.entity.BoatEntityRenderer;
import com.alfred.woodapi.render.entity.ChestBoatEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;

public class WoodApiClient implements ClientModInitializer {
	@Override
	@SuppressWarnings("unchecked")
	public void onInitializeClient() {
		for (Wood wood : Wood.WOODS) {
			BlockRenderLayerMap.INSTANCE.putBlock(wood.door, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(wood.trapdoor, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(wood.sapling, RenderLayer.getCutout());

			EntityRendererRegistry.register(wood.boatEntity, BoatEntityRenderer::new);
			EntityRendererRegistry.register(wood.chestBoatEntity, ChestBoatEntityRenderer::new);

			BlockEntityRendererFactories.register((BlockEntityType<SignBlockEntity>) (Object) wood.signEntity, SignBlockEntityRenderer::new);
			BlockEntityRendererFactories.register((BlockEntityType<SignBlockEntity>) (Object) wood.hangingSignEntity, HangingSignBlockEntityRenderer::new);
		}
	}
}