package net.tv337.witchery2.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tv337.witchery2.Witchery;
import net.tv337.witchery2.block.ModBlocks;

@Mod.EventBusSubscriber(
        modid = Witchery.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModClientSetup {


    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {

        ItemBlockRenderTypes.setRenderLayer(
                ModBlocks.WITCH_CAULDRON.get(),
                RenderType.translucent()
        );
    }
}