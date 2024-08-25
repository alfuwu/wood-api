package com.alfred.woodapi.block;

import com.alfred.woodapi.block.entity.HangingSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class HangingSignBlock extends net.minecraft.block.HangingSignBlock {
    public HangingSignBlock(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSignBlockEntity(pos, state);
    }
}
