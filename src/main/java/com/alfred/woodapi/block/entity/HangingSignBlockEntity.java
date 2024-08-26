package com.alfred.woodapi.block.entity;

import com.alfred.woodapi.registry.Wood;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class HangingSignBlockEntity extends net.minecraft.block.entity.HangingSignBlockEntity {
    protected final Wood wood;

    public HangingSignBlockEntity(BlockPos blockPos, BlockState blockState, Wood wood) {
        super(blockPos, blockState);
        this.wood = wood;
    }

    @Override
    public BlockEntityType<?> getType() {
        return wood.hangingSignEntity;
    }
}
