package com.alfred.woodapi.block.entity;

import com.alfred.woodapi.registry.Wood;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class SignBlockEntity extends net.minecraft.block.entity.SignBlockEntity {
    protected final Wood wood;

    public SignBlockEntity(BlockPos pos, BlockState state, Wood wood) {
        super(pos, state);
        this.wood = wood;
    }

    @Override
    public BlockEntityType<?> getType() {
        return this.wood.signEntity;
    }
}