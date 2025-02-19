package net.tv337.witchery2.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tv337.witchery2.Witchery;
import net.tv337.witchery2.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Witchery.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WITCHERY_TAB = CREATIVE_MODE_TABS.register("witchery_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                    .title(Component.translatable("creativetab.witchery_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        for(RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                            pOutput.accept(item.get());
                        }
                        for(RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
                            pOutput.accept(block.get());
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
