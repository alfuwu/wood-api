package com.alfred.woodapi.registry;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items {
	private static final Map<RegistryKey<ItemGroup>, List<Pair<Item, @Nullable Item>>> ITEMS = new HashMap<>();

	public static <T extends Item> T register(Identifier id, T item) {
		return Registry.register(Registries.ITEM, id, item);
	}

	public static <T extends Item> T register(Identifier id, T item, @Nullable Item after, RegistryKey<ItemGroup> group) {
		add(item, after, group);
		return register(id, item);
	}

	public static <T extends Block> T register(Identifier id, T block, @Nullable Item after, RegistryKey<ItemGroup> group) {
		register(id, new BlockItem(block, new FabricItemSettings()), after, group);
		return block;
	}

	public static void add(Item item, @Nullable Item after, RegistryKey<ItemGroup> group) {
		if (ITEMS.containsKey(group)) {
			List<Pair<Item, @Nullable Item>> pairs = ITEMS.get(group);
			pairs.add(Pair.of(item, after));
		} else {
			ITEMS.put(group, new ArrayList<>() {{ add(Pair.of(item, after)); }});
		}
	}

	public static void register() {
		for (Map.Entry<RegistryKey<ItemGroup>, List<Pair<Item, @Nullable Item>>> items : ITEMS.entrySet()) {
			ItemGroupEvents.modifyEntriesEvent(items.getKey()).register(content -> {
				for (Pair<Item, @Nullable Item> pair : items.getValue())
					if (pair.getSecond() == null)
						content.add(pair.getFirst());
					else
						content.addAfter(pair.getSecond(), pair.getFirst());
			});
		}
	}
}