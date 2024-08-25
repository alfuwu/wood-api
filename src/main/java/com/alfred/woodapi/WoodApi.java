package com.alfred.woodapi;

import com.alfred.woodapi.registry.Blocks;
import com.alfred.woodapi.registry.EntityTypes;
import com.alfred.woodapi.registry.Items;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WoodApi implements ModInitializer {
	@Override
	public void onInitialize() {
		Blocks.register();
		Items.register();
		//EntityTypes.register();
	}

	public static Identifier identifier(String path) {
		return new Identifier("wood-api", path);
	}
}