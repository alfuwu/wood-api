package com.alfred.woodapi.block;

import com.alfred.woodapi.block.entity.HangingSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class WallHangingSignBlock extends net.minecraft.block.WallHangingSignBlock {
    public WallHangingSignBlock(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSignBlockEntity(pos, state);
    }
}
