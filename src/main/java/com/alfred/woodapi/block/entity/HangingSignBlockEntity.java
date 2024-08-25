package com.alfred.woodapi.block.entity;

import com.alfred.woodapi.registry.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class HangingSignBlockEntity extends net.minecraft.block.entity.HangingSignBlockEntity {
    public HangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntities.HANGING_SIGN;
    }
}
