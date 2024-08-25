package com.alfred.woodapi.block.entity;

import com.alfred.woodapi.registry.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class SignBlockEntity extends net.minecraft.block.entity.SignBlockEntity {
    public SignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntities.SIGN;
    }
}