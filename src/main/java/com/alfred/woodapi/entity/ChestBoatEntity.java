package com.alfred.woodapi.entity;

import com.alfred.woodapi.registry.EntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;

public class ChestBoatEntity extends net.minecraft.entity.vehicle.ChestBoatEntity {
    public static TrackedData<Integer> BOAT_TYPE = DataTracker.registerData(ChestBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ChestBoatEntity(EntityType<? extends ChestBoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static ChestBoatEntity create(World world, BoatEntity.BoatType type, double x, double y, double z) {
        ChestBoatEntity entity = new ChestBoatEntity(EntityTypes.CHEST_BOAT_ENTITY, world);
        entity.setPosition(x, y, z);
        entity.prevX = x;
        entity.prevY = y;
        entity.prevZ = z;
        entity.setBoatType(type);
        return entity;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getBoatType().getIndex());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Type", NbtElement.INT_TYPE))
            this.setBoatType(BoatEntity.BoatType.byIndex(nbt.getInt("Type")));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOAT_TYPE, 0);
    }

    public void setBoatType(BoatEntity.BoatType type) {
        this.dataTracker.set(BOAT_TYPE, type.getIndex());
    }

    public BoatEntity.BoatType getBoatType() {
        return BoatEntity.BoatType.byIndex(this.dataTracker.get(BOAT_TYPE));
    }

    @Override
    public Item asItem() {
        return this.getBoatType().getChestBoatItem();
    }

    @Override
    public void setVariant(Type type) {

    }

    @Override
    public Type getVariant() {
        return Type.OAK;
    }
}
