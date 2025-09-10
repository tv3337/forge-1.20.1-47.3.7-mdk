package net.tv337.witchery2.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tv337.witchery2.Witchery;
import net.tv337.witchery2.block.entity.witch_cauldron;
import net.tv337.witchery2.item.ModItems;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Witchery.MOD_ID);
    //Creating blocks
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RAW_SAPPHIRE_BLOCK = registerBlock("raw_sapphire_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).sound(SoundType.AMETHYST)));
    //Tutorial Blocks

    public static final RegistryObject<Block> GRASSPER = registerBlock("grassper",
            () -> new grassper(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)));
    public static final RegistryObject<Block> WITCH_CAULDRON = registerBlock("witch_cauldron",
            () -> new witch_cauldron(BlockBehaviour.Properties.copy((Blocks.CAULDRON))));








    //Blocks Above
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static  void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
