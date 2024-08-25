package com.alfred.woodapi.registry;

import com.alfred.woodapi.Wood;
import com.alfred.woodapi.WoodApi;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Blocks {
	public static final List<Pair<Block, Identifier>> BLOCKS = new ArrayList<>();
	public static final Wood RUBBER_WOOD = Wood.create(WoodApi.identifier("rubber"), null);
	//public static final Block RUBBER_TREE_SAPLING = register("rubber_tree_sapling", new SaplingBlock(new RubberSaplingGenerator("rubber"), FabricBlockSettings.create().mapColor(MapColor.DARK_GREEN).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY)), Items.CHERRY_SAPLING, ItemGroups.NATURAL);

	public static <T extends Block> T register(Identifier id, T block, @Nullable Item after, RegistryKey<ItemGroup> group) {
		BLOCKS.add(Pair.of(block, id));
		Items.register(id, block, after, group);
		return block;
	}

	public static void register() {
		BlockEntities.register();
		for (Pair<Block, Identifier> block : BLOCKS)
			Registry.register(Registries.BLOCK, block.getSecond(), block.getFirst());
	}

	public static Item get(Block block) {
		return BlockItem.BLOCK_ITEMS.get(block);
	}

	public static Block createButtonBlock(BlockSetType type) {
		return new ButtonBlock(type, 30, AbstractBlock.Settings.create().noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY));
	}
}