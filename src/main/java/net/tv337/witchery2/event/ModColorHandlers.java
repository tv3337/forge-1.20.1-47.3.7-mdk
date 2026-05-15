package net.tv337.witchery2.event;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tv337.witchery2.Witchery;
import net.tv337.witchery2.block.ModBlocks;
import net.tv337.witchery2.block.entity.WitchCauldronBe;

@Mod.EventBusSubscriber(
        modid = Witchery.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModColorHandlers {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

        event.register((state, level, pos, tintIndex) -> {

            if (tintIndex != 0 || level == null || pos == null) {
                return -1;
            }

            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof WitchCauldronBe cauldron) {
                return cauldron.getWaterColor();
            }

            return 0x3F76E4;

        }, ModBlocks.WITCH_CAULDRON.get());
    }
}