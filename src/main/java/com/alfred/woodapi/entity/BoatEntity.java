package com.alfred.woodapi.entity;

import com.alfred.woodapi.registry.Wood;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BoatEntity extends net.minecraft.entity.vehicle.BoatEntity {
    public static TrackedData<String> BOAT_TYPE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.STRING);

    public BoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unchecked")
    public static BoatEntity create(World world, BoatType type, EntityType<?> entityType, double x, double y, double z) {
        BoatEntity entity = new BoatEntity((EntityType<? extends BoatEntity>) entityType, world);
        entity.setPosition(x, y, z);
        entity.prevX = x;
        entity.prevY = y;
        entity.prevZ = z;
        entity.setBoatType(type);
        return entity;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("Type", this.getBoatType().getId().toString());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Type", NbtElement.STRING_TYPE) && BoatType.byId(new Identifier(nbt.getString("Type"))) != null)
            this.setBoatType(BoatType.byId(new Identifier(nbt.getString("Type"))));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOAT_TYPE, "");
    }

    public void setBoatType(BoatType type) {
        this.dataTracker.set(BOAT_TYPE, type.getId().toString());
    }

    public BoatType getBoatType() {
        return BoatType.byId(new Identifier(this.dataTracker.get(BOAT_TYPE)));
    }

    @Override
    public Item asItem() {
        return this.getBoatType().getBoatItem();
    }

    @Override
    public void setVariant(Type type) {

    }

    @Override
    public Type getVariant() {
        return Type.OAK;
    }

    public static class BoatType {
        private final Identifier id;
        private final Block baseBlock;
        private Item boatItem;
        private Item chestBoatItem;
        private boolean raftLike = false;

        public BoatType(Block planks, Identifier id) {
            this.id = id;
            this.baseBlock = planks;
        }

        public final void setItems(Item boatItem, Item chestBoatItem) {
            this.boatItem = boatItem;
            this.chestBoatItem = chestBoatItem;
        }

        public void setRaftLike(boolean bl) {
            this.raftLike = bl;
        }

        public String toString() {
            return this.id.toString();
        }

        public Block getBaseBlock() {
            return this.baseBlock;
        }

        public Item getBoatItem() {
            return this.boatItem;
        }

        public Item getChestBoatItem() {
            return this.chestBoatItem;
        }

        public boolean isRaftLike() {
            return raftLike;
        }

        public Identifier getId() {
            return this.id;
        }

        public static BoatType byId(Identifier id) {
            for (BoatType type : Wood.BOAT_TYPES)
                if (type.id.equals(id))
                    return type;
            return null;
        }
    }
}
