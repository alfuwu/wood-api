package com.alfred.woodapi.registry;

import com.alfred.woodapi.block.HangingSignBlock;
import com.alfred.woodapi.block.SignBlock;
import com.alfred.woodapi.block.WallHangingSignBlock;
import com.alfred.woodapi.block.WallSignBlock;
import com.alfred.woodapi.block.entity.HangingSignBlockEntity;
import com.alfred.woodapi.block.entity.SignBlockEntity;
import com.alfred.woodapi.entity.BoatEntity;
import com.alfred.woodapi.entity.ChestBoatEntity;
import com.alfred.woodapi.item.BoatItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.block.Blocks.createLeavesBlock;
import static net.minecraft.block.Blocks.createLogBlock;
import static net.minecraft.item.Items.*;
import static net.minecraft.block.Blocks.OAK_SAPLING;

public class Wood {
    public static final List<Wood> WOODS = new ArrayList<>();
    public static final List<BoatEntity.BoatType> BOAT_TYPES = new ArrayList<>();

    public Block log, leaves, wood, planks, strippedLog, strippedWood, sign, wallSign,
            hangingSign, wallHangingSign, fence, fenceGate, door, slab, stairs, button, pressurePlate, trapdoor, sapling;
    public SaplingGenerator saplingGenerator;
    public Item signItem, hangingSignItem;
    public BlockEntityType<SignBlockEntity> signEntity;
    public BlockEntityType<HangingSignBlockEntity> hangingSignEntity;
    public BoatEntity.BoatType boatType;
    public Item boat, chestBoat;
    public EntityType<BoatEntity> boatEntity;
    public EntityType<ChestBoatEntity> chestBoatEntity;

    public WoodType woodType;
    public BlockSetType blockSetType;
    public Identifier name;

    private Wood() {
        WOODS.add(this);
    }

    private static <T extends Block> T register(Identifier id, T block, Item after, RegistryKey<ItemGroup> group) {
        Registry.register(Registries.BLOCK, id, block);
        register(id, new BlockItem(block, new FabricItemSettings()), after, group);
        return block;
    }

    private static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    private static <T extends Item> T register(Identifier id, T item, @Nullable Item after, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
            if (after != null)
                content.addAfter(after, item);
            else
                content.add(item);
        });
        return register(id, item);
    }

    private static <T extends Item> T add(T item, @Nullable Item after, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
            if (after != null)
                content.addAfter(after, item);
            else
                content.add(item);
        });
        return item;
    }

    public static Item get(Block block) {
        return BlockItem.BLOCK_ITEMS.get(block);
    }

    public static Block createButtonBlock(BlockSetType type) {
        return new ButtonBlock(type, 30, AbstractBlock.Settings.create().noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY));
    }

    // wood go brrrr
    public static Wood create(Identifier name, Settings settings) {
        Wood wood = new Wood();

        wood.blockSetType = new BlockSetType(name.toString());
        wood.woodType = WoodType.register(new WoodType(name.toString(), wood.blockSetType));
        wood.name = name;

        wood.sign = Registry.register(Registries.BLOCK, wood.name.withSuffixedPath("_sign"), new SignBlock(wood.woodType, FabricBlockSettings.create(), wood));
        wood.hangingSign = Registry.register(Registries.BLOCK, wood.name.withSuffixedPath("_hanging_sign"), new HangingSignBlock(wood.woodType, FabricBlockSettings.create(), wood));
        wood.wallSign = Registry.register(Registries.BLOCK, wood.name.withSuffixedPath("_wall_sign"), new WallSignBlock(wood.woodType, FabricBlockSettings.create(), wood));
        wood.wallHangingSign = Registry.register(Registries.BLOCK, wood.name.withSuffixedPath("_wall_hanging_sign"), new WallHangingSignBlock(wood.woodType, FabricBlockSettings.create(), wood));

        wood.signItem = register(wood.name.withSuffixedPath("_sign"), new SignItem(new FabricItemSettings(), wood.sign, wood.wallSign), BAMBOO_HANGING_SIGN, ItemGroups.FUNCTIONAL);
        wood.hangingSignItem = register(wood.name.withSuffixedPath("_hanging_sign"), new HangingSignItem(wood.hangingSign, wood.wallHangingSign, new FabricItemSettings()), wood.signItem, ItemGroups.FUNCTIONAL);

        wood.leaves = register(wood.name.withSuffixedPath("_leaves"), createLeavesBlock(BlockSoundGroup.GRASS), CHERRY_LEAVES, ItemGroups.NATURAL);

        wood.log = register(wood.name.withSuffixedPath("_log"), createLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN), BAMBOO_BUTTON, ItemGroups.BUILDING_BLOCKS);
        add(get(wood.log), CHERRY_LOG, ItemGroups.NATURAL);
        wood.wood = register(wood.name.withSuffixedPath("_wood"), new PillarBlock(FabricBlockSettings.create()), get(wood.log), ItemGroups.BUILDING_BLOCKS);

        wood.strippedLog = register(wood.name.withPrefixedPath("stripped_").withSuffixedPath("_log"), createLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN), get(wood.wood), ItemGroups.BUILDING_BLOCKS);
        wood.strippedWood = register(wood.name.withPrefixedPath("stripped_").withSuffixedPath("_wood"), new PillarBlock(FabricBlockSettings.create()), get(wood.strippedLog), ItemGroups.BUILDING_BLOCKS);

        wood.planks = register(wood.name.withSuffixedPath("_planks"), new Block(FabricBlockSettings.create()), get(wood.strippedWood), ItemGroups.BUILDING_BLOCKS);

        wood.stairs = register(wood.name.withSuffixedPath("_stairs"), new StairsBlock(wood.planks.getDefaultState(), AbstractBlock.Settings.copy(wood.planks)), get(wood.planks), ItemGroups.BUILDING_BLOCKS);
        wood.slab = register(wood.name.withSuffixedPath("_slab"), new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(Instrument.BASS).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).burnable()), get(wood.stairs), ItemGroups.BUILDING_BLOCKS);

        wood.fence = register(wood.name.withSuffixedPath("_fence"), new FenceBlock(FabricBlockSettings.create()), get(wood.slab), ItemGroups.BUILDING_BLOCKS);
        wood.fenceGate = register(wood.name.withSuffixedPath("_fence_gate"), new FenceGateBlock(wood.woodType, FabricBlockSettings.create()), get(wood.fence), ItemGroups.BUILDING_BLOCKS);

        wood.door = register(wood.name.withSuffixedPath("_door"), new DoorBlock(wood.blockSetType, FabricBlockSettings.create()), get(wood.fenceGate), ItemGroups.BUILDING_BLOCKS);
        wood.trapdoor = register(wood.name.withSuffixedPath("_trapdoor"), new TrapdoorBlock(wood.blockSetType, FabricBlockSettings.create()), get(wood.door), ItemGroups.BUILDING_BLOCKS);
        wood.pressurePlate = register(wood.name.withSuffixedPath("_pressure_plate"), new PressurePlateBlock(wood.blockSetType, FabricBlockSettings.create()), get(wood.trapdoor), ItemGroups.BUILDING_BLOCKS);
        wood.button = register(wood.name.withSuffixedPath("_button"), createButtonBlock(wood.blockSetType), get(wood.pressurePlate), ItemGroups.BUILDING_BLOCKS);
        //add(get(wood.button), LEVER, ItemGroups.REDSTONE);

        wood.saplingGenerator = new SaplingGenerator(wood.name.toString(), 0.1F, Optional.empty(), Optional.empty(), Optional.of(TreeConfiguredFeatures.OAK), Optional.of(TreeConfiguredFeatures.FANCY_OAK), Optional.of(TreeConfiguredFeatures.OAK_BEES_005), Optional.of(TreeConfiguredFeatures.FANCY_OAK_BEES_005));
        wood.sapling = register(wood.name.withSuffixedPath("_sapling"), new SaplingBlock(wood.saplingGenerator, AbstractBlock.Settings.copyShallow(OAK_SAPLING)), CHERRY_SAPLING, ItemGroups.NATURAL);


        // entities :)
        wood.signEntity = Registry.register(Registries.BLOCK_ENTITY_TYPE, wood.name.withSuffixedPath("_sign"),
                BlockEntityType.Builder.create((pos, state) -> {
                    return new SignBlockEntity(pos, state, wood);
                }, wood.sign, wood.wallSign).build());
        //Registry.register(Registries.BLOCK_ENTITY_TYPE, wood.name.withSuffixedPath("_wall_sign"),
        //        BlockEntityType.Builder.create((pos, state) -> {
        //            return new SignBlockEntity(pos, state, wood);
        //        }, wood.wallSign).build());
        wood.hangingSignEntity = Registry.register(Registries.BLOCK_ENTITY_TYPE, wood.name.withSuffixedPath("_hanging_sign"),
                BlockEntityType.Builder.create((pos, state) -> {
                    return new HangingSignBlockEntity(pos, state, wood);
                }, wood.hangingSign, wood.wallHangingSign).build());

        wood.boatEntity = Registry.register(Registries.ENTITY_TYPE, wood.name.withSuffixedPath("boat"),
                EntityType.Builder.create(BoatEntity::new, SpawnGroup.MISC).setDimensions(1.375f, 0.5625f)
                        .maxTrackingRange(10).build());
        wood.chestBoatEntity = Registry.register(Registries.ENTITY_TYPE, wood.name.withSuffixedPath("_chest_boat"),
                EntityType.Builder.create(ChestBoatEntity::new, SpawnGroup.MISC).setDimensions(1.375f, 0.5625f)
                        .maxTrackingRange(10).build());

        wood.boatType = new BoatEntity.BoatType(wood.planks, wood.name);
        BOAT_TYPES.add(wood.boatType);
        wood.boat = register(wood.name.withSuffixedPath("_boat"), new BoatItem(false, wood.boatType, wood.boatEntity, new FabricItemSettings()), BAMBOO_CHEST_RAFT, ItemGroups.TOOLS);
        wood.chestBoat = register(wood.name.withSuffixedPath("_chest_boat"), new BoatItem(true, wood.boatType, wood.chestBoatEntity, new FabricItemSettings()), wood.boat, ItemGroups.TOOLS);
        wood.boatType.setItems(wood.boat, wood.chestBoat);

        return wood;
    }

    public static class Settings {

    }
}