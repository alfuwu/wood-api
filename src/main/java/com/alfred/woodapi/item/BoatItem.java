package com.alfred.woodapi.item;

import com.alfred.woodapi.entity.BoatEntity;
import com.alfred.woodapi.entity.ChestBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoatItem extends net.minecraft.item.BoatItem {
    private final BoatEntity.BoatType type;
    private final boolean chest;
    private final EntityType<?> entityType;

    public BoatItem(boolean chest, BoatEntity.BoatType type, EntityType<?> entityType, Settings settings) {
        super(chest, net.minecraft.entity.vehicle.BoatEntity.Type.OAK, settings);
        this.type = type;
        this.chest = chest;
        this.entityType = entityType;
    }

    @Override
    protected net.minecraft.entity.vehicle.BoatEntity createEntity(World world, HitResult hitResult, ItemStack stack, PlayerEntity player) {
        Vec3d vec3d = hitResult.getPos();
        net.minecraft.entity.vehicle.BoatEntity boat = this.chest ? ChestBoatEntity.create(world, this.type, this.entityType, vec3d.x, vec3d.y, vec3d.z) : BoatEntity.create(world, this.type, this.entityType, vec3d.x, vec3d.y, vec3d.z);
        if (world instanceof ServerWorld serverWorld)
            EntityType.copier(serverWorld, stack, player).accept(boat);
        return boat;
    }
}
