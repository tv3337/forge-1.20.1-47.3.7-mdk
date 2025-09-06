package net.tv337.witchery2;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tv337.witchery2.block.ModBlocks;
import net.tv337.witchery2.effect.ModEffects;
import net.tv337.witchery2.item.*;
import org.slf4j.Logger;


/*
* Change the public static final String Mod_ID to whatever you want
* change the @Mod() to whatever you want
* change the package to net.tv337.TheNameOfTheMod
* rename this file to TheNameOfTheMod
* */


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Witchery.MOD_ID)
public class Witchery{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "witchery2";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public Witchery(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event){

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.RAW_SAPPHIRE);
            event.accept(ModItems.ANOINTING_PASTE);
            event.accept(ModItems.WORMY_APPLE);
            event.accept(ModItems.ARTICHOKE_BULB);
            event.accept(ModItems.ARMOR_PROTECTION_POPPET);
            event.accept(ModItems.ATTUNED_STONE);
            event.accept(ModItems.ATTUNED_STONE_CHARGED);
            event.accept(ModItems.BALL_OF_BATS);
            event.accept(ModItems.BONE_NEEDLE);
            event.accept(ModItems.BIOME_NOTE);
            event.accept(ModItems.BELLADONNA_FLOWER);
            event.accept(ModItems.BELLADONNA_SEEDS);
            event.accept(ModItems.DEMON_HEART);
            event.accept(ModItems.CONDENSED_FEAR);
            event.accept(ModItems.CREEPER_HEART);
            event.accept(ModItems.BROOMSTICK);
            event.accept(ModItems.WOVEN_CRUOR);
            event.accept(ModItems.CIRCLE_TALISMAN_EMPTY);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){

        }
    }
}
