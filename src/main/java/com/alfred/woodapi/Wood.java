package com.alfred.woodapi;

import com.alfred.woodapi.block.HangingSignBlock;
import com.alfred.woodapi.block.SignBlock;
import com.alfred.woodapi.entity.BoatEntity;
import com.alfred.woodapi.item.BoatItem;
import com.alfred.woodapi.registry.Blocks;
import com.alfred.woodapi.registry.Items;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alfred.woodapi.registry.Blocks.createButtonBlock;
import static net.minecraft.block.Blocks.createLeavesBlock;
import static net.minecraft.block.Blocks.createLogBlock;
import static net.minecraft.item.Items.*;
import static net.minecraft.block.Blocks.OAK_SAPLING;

public class Wood {
    public static List<Wood> WOODS = new ArrayList<>();
    public Block log, leaves, wood, planks, strippedLog, strippedWood, sign, wallSign,
            hangingSign, wallHangingSign, fence, fenceGate, door, slab, stairs, button, pressurePlate, trapdoor, sapling;
    public SaplingGenerator saplingGenerator;
    public Item signItem, hangingSignItem;
    public BoatEntity.BoatType boatType;
    public Item boat;
    public Item chestBoat;

    public WoodType woodType;
    public BlockSetType blockSetType;
    public Identifier name;

    private Wood() {
        WOODS.add(this);
    }

    // wood go brrrr
    public static Wood create(Identifier name, Settings settings) {
        Wood wood = new Wood();

        wood.blockSetType = new BlockSetType(name.toString());
        wood.woodType = WoodType.register(new WoodType(name.toString(), wood.blockSetType));
        wood.name = name;

        wood.sign = new SignBlock(wood.woodType, FabricBlockSettings.create());
        wood.hangingSign = new HangingSignBlock(wood.woodType, FabricBlockSettings.create());
        wood.wallSign = new WallSignBlock(wood.woodType, FabricBlockSettings.create());
        wood.wallHangingSign = new WallHangingSignBlock(wood.woodType, FabricBlockSettings.create());
        Blocks.BLOCKS.add(Pair.of(wood.sign, wood.name.withSuffixedPath("_sign")));
        Blocks.BLOCKS.add(Pair.of(wood.hangingSign, wood.name.withSuffixedPath("_hanging_sign")));
        Blocks.BLOCKS.add(Pair.of(wood.wallSign, wood.name.withSuffixedPath("_wall_sign")));
        Blocks.BLOCKS.add(Pair.of(wood.wallHangingSign, wood.name.withSuffixedPath("_wall_hanging_sign")));
        wood.signItem = Items.register(wood.name.withSuffixedPath("_sign"), new SignItem(new FabricItemSettings(), wood.sign, wood.wallSign), BAMBOO_HANGING_SIGN, ItemGroups.FUNCTIONAL);
        wood.hangingSignItem = Items.register(wood.name.withSuffixedPath("_hanging_sign"), new HangingSignItem(wood.hangingSign, wood.wallHangingSign, new FabricItemSettings()), wood.signItem, ItemGroups.FUNCTIONAL);

        wood.leaves = Blocks.register(wood.name.withSuffixedPath("_leaves"), createLeavesBlock(BlockSoundGroup.GRASS), CHERRY_LEAVES, ItemGroups.NATURAL);

        wood.log = Blocks.register(wood.name.withSuffixedPath("_log"), createLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN), BAMBOO_BUTTON, ItemGroups.BUILDING_BLOCKS);
        Items.add(Blocks.get(wood.log), CHERRY_LOG, ItemGroups.NATURAL);
        wood.wood = Blocks.register(wood.name.withSuffixedPath("_wood"), new PillarBlock(FabricBlockSettings.create()), Blocks.get(wood.log), ItemGroups.BUILDING_BLOCKS);

        wood.strippedLog = Blocks.register(wood.name.withPrefixedPath("stripped_").withSuffixedPath("_log"), createLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN), Blocks.get(wood.wood), ItemGroups.BUILDING_BLOCKS);
        wood.strippedWood = Blocks.register(wood.name.withPrefixedPath("stripped_").withSuffixedPath("_wood"), new PillarBlock(FabricBlockSettings.create()), Blocks.get(wood.strippedLog), ItemGroups.BUILDING_BLOCKS);

        wood.planks = Blocks.register(wood.name.withSuffixedPath("_planks"), new Block(FabricBlockSettings.create()), Blocks.get(wood.strippedWood), ItemGroups.BUILDING_BLOCKS);

        wood.stairs = Blocks.register(wood.name.withSuffixedPath("_stairs"), new StairsBlock(wood.planks.getDefaultState(), AbstractBlock.Settings.copy(wood.planks)), Blocks.get(wood.planks), ItemGroups.BUILDING_BLOCKS);
        wood.slab = Blocks.register(wood.name.withSuffixedPath("_slab"), new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(Instrument.BASS).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).burnable()), Blocks.get(wood.stairs), ItemGroups.BUILDING_BLOCKS);

        wood.fence = Blocks.register(wood.name.withSuffixedPath("_fence"), new FenceBlock(FabricBlockSettings.create()), Blocks.get(wood.slab), ItemGroups.BUILDING_BLOCKS);
        wood.fenceGate = Blocks.register(wood.name.withSuffixedPath("_fence_gate"), new FenceGateBlock(wood.woodType, FabricBlockSettings.create()), Blocks.get(wood.fence), ItemGroups.BUILDING_BLOCKS);

        wood.door = Blocks.register(wood.name.withSuffixedPath("_door"), new DoorBlock(wood.blockSetType, FabricBlockSettings.create()), Blocks.get(wood.fenceGate), ItemGroups.BUILDING_BLOCKS);
        wood.trapdoor = Blocks.register(wood.name.withSuffixedPath("_trapdoor"), new TrapdoorBlock(wood.blockSetType, FabricBlockSettings.create()), Blocks.get(wood.door), ItemGroups.BUILDING_BLOCKS);
        wood.pressurePlate = Blocks.register(wood.name.withSuffixedPath("_pressure_plate"), new PressurePlateBlock(wood.blockSetType, FabricBlockSettings.create()), Blocks.get(wood.trapdoor), ItemGroups.BUILDING_BLOCKS);
        wood.button = Blocks.register(wood.name.withSuffixedPath("_button"), createButtonBlock(wood.blockSetType), Blocks.get(wood.pressurePlate), ItemGroups.BUILDING_BLOCKS);
        //Items.add(get(wood.button), LEVER, ItemGroups.REDSTONE);

        wood.saplingGenerator = new SaplingGenerator(wood.name.toString(), 0.1F, Optional.empty(), Optional.empty(), Optional.of(TreeConfiguredFeatures.OAK), Optional.of(TreeConfiguredFeatures.FANCY_OAK), Optional.of(TreeConfiguredFeatures.OAK_BEES_005), Optional.of(TreeConfiguredFeatures.FANCY_OAK_BEES_005));
        wood.sapling = Blocks.register(wood.name.withSuffixedPath("_sapling"), new SaplingBlock(wood.saplingGenerator, AbstractBlock.Settings.copyShallow(OAK_SAPLING)), CHERRY_SAPLING, ItemGroups.NATURAL);

        wood.boatType = new BoatEntity.BoatType(wood.planks, wood.name);
        wood.boat = Items.register(wood.name.withSuffixedPath("_boat"), new BoatItem(false, wood.boatType, new FabricItemSettings()));
        wood.chestBoat = Items.register(wood.name.withSuffixedPath("_chest_boat"), new BoatItem(true, wood.boatType, new FabricItemSettings()));
        wood.boatType.setItems(wood.boat, wood.chestBoat);

        return wood;
    }

    public static class Settings {

    }
}