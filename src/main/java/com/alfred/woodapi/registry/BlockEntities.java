package com.alfred.woodapi.registry;

import com.alfred.woodapi.Wood;
import com.alfred.woodapi.WoodApi;
import com.alfred.woodapi.block.entity.SignBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntities {
    public static final BlockEntityType<?> SIGN;
    public static final BlockEntityType<?> HANGING_SIGN;

    public static void register() {

    }

    static {
        FabricBlockEntityTypeBuilder<SignBlockEntity> signBuilder = FabricBlockEntityTypeBuilder.create(SignBlockEntity::new);
        FabricBlockEntityTypeBuilder<SignBlockEntity> hangingSignBuilder = FabricBlockEntityTypeBuilder.create(SignBlockEntity::new);

        for (Wood wood : Wood.WOODS) {
            signBuilder.addBlocks(wood.sign, wood.wallSign);
            hangingSignBuilder.addBlocks(wood.hangingSign, wood.wallHangingSign);
        }

        SIGN = Registry.register(Registries.BLOCK_ENTITY_TYPE, WoodApi.identifier("sign"), signBuilder.build());
        HANGING_SIGN = Registry.register(Registries.BLOCK_ENTITY_TYPE, WoodApi.identifier("hanging_sign"), hangingSignBuilder.build());
    }
}
