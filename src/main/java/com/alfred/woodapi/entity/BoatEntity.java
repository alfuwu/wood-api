package com.alfred.woodapi.entity;

import com.alfred.woodapi.registry.EntityTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BoatEntity extends net.minecraft.entity.vehicle.BoatEntity {
    public static TrackedData<Integer> BOAT_TYPE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static BoatEntity create(World world, BoatType type, double x, double y, double z) {
        BoatEntity entity = new BoatEntity(EntityTypes.BOAT_ENTITY, world);
        entity.setPosition(x, y, z);
        entity.prevX = x;
        entity.prevY = y;
        entity.prevZ = z;
        entity.setBoatType(type);
        return entity;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Type", this.getBoatType().getIndex());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Type", NbtElement.INT_TYPE))
            this.setBoatType(BoatType.byIndex(nbt.getInt("Type")));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOAT_TYPE, 0);
    }

    public void setBoatType(BoatType type) {
        this.dataTracker.set(BOAT_TYPE, type.getIndex());
    }

    public BoatType getBoatType() {
        return BoatType.byIndex(this.dataTracker.get(BOAT_TYPE));
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
            EntityTypes.BOAT_TYPES.add(this);
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

        public int getIndex() {
            return EntityTypes.BOAT_TYPES.indexOf(this);
        }

        public Identifier getId() {
            return this.id;
        }

        public static BoatType byIndex(int type) {
            return EntityTypes.BOAT_TYPES.get(type);
        }

        public static BoatType byId(Identifier id) {
            for (BoatType type : EntityTypes.BOAT_TYPES)
                if (type.id.equals(id))
                    return type;
            return null;
        }
    }
}
