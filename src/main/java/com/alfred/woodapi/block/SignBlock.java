package com.alfred.woodapi.block;

import com.alfred.woodapi.registry.Wood;
import com.alfred.woodapi.block.entity.SignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SignBlock extends net.minecraft.block.SignBlock {
    protected final Wood wood;

    public SignBlock(WoodType woodType, Settings settings, Wood wood) {
        super(woodType, settings);
        this.wood = wood;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SignBlockEntity(pos, state, this.wood);
    }
}
