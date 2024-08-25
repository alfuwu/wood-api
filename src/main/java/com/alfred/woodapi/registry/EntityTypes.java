package com.alfred.woodapi.registry;

import com.alfred.woodapi.WoodApi;
import com.alfred.woodapi.entity.BoatEntity;
import com.alfred.woodapi.entity.ChestBoatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class EntityTypes {
    public static final List<BoatEntity.BoatType> BOAT_TYPES = new ArrayList<>();

    // TODO: give every wood type their own EntityTypes?
    // it'd be kinda awkward if there was a mod that showed the client the ID of any entity the client looked at
    // and boats showed up as "wood-api:wood_boat" or something
    // "more_wood:wood_boat" is much better
    // but that would also register a LOT of EntityTypes if a mod added a lot of wood types
    public static final EntityType<BoatEntity> BOAT_ENTITY = Registry.register(Registries.ENTITY_TYPE, WoodApi.identifier("boat"),
            EntityType.Builder.create(BoatEntity::new, SpawnGroup.MISC).setDimensions(1.375f, 0.5625f)
                    .maxTrackingRange(10).build());
    public static final EntityType<ChestBoatEntity> CHEST_BOAT_ENTITY = Registry.register(Registries.ENTITY_TYPE, WoodApi.identifier("chest_boat"),
            EntityType.Builder.create(ChestBoatEntity::new, SpawnGroup.MISC).setDimensions(1.375f, 0.5625f)
                    .maxTrackingRange(10).build());
}