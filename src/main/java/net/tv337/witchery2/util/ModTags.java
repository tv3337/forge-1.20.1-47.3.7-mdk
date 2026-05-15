package net.tv337.witchery2.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.tv337.witchery2.Witchery;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> CAULDRON_HEAT_SOURCES =
                TagKey.create(
                        Registries.BLOCK,
                        new ResourceLocation(
                                Witchery.MOD_ID,
                                "cauldron_heat_sources"
                        )
                );
    }
}