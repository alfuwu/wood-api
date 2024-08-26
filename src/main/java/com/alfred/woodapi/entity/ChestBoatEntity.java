package com.alfred.woodapi.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ChestBoatEntity extends net.minecraft.entity.vehicle.ChestBoatEntity {
    public static TrackedData<String> BOAT_TYPE = DataTracker.registerData(ChestBoatEntity.class, TrackedDataHandlerRegistry.STRING);

    public ChestBoatEntity(EntityType<? extends ChestBoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unchecked")
    public static ChestBoatEntity create(World world, BoatEntity.BoatType type, EntityType<?> entityType, double x, double y, double z) {
        ChestBoatEntity entity = new ChestBoatEntity((EntityType<? extends ChestBoatEntity>) entityType, world);
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
        nbt.putString("Type", this.getBoatType().getId().toString());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Type", NbtElement.STRING_TYPE) && BoatEntity.BoatType.byId(new Identifier(nbt.getString("Type"))) != null)
           this.setBoatType(BoatEntity.BoatType.byId(new Identifier(nbt.getString("Type"))));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOAT_TYPE, "");
    }

    public void setBoatType(BoatEntity.BoatType type) {
        this.dataTracker.set(BOAT_TYPE, type.getId().toString());
    }

    public BoatEntity.BoatType getBoatType() {
        return BoatEntity.BoatType.byId(new Identifier(this.dataTracker.get(BOAT_TYPE)));
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
