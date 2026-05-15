package net.tv337.witchery2.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.tv337.witchery2.block.entity.WitchCauldronBe;

public class CauldronUtils {

    public static void setCauldronColor(Level level, BlockPos pos, int color) {

        if (level.isClientSide) return;

        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof WitchCauldronBe cauldron) {
            cauldron.setWaterColor(color);
        }
    }
}