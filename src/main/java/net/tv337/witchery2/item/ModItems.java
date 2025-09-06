package net.tv337.witchery2.item;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tv337.witchery2.*;
import net.tv337.witchery2.item.custom.attuned_stone_charged;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Witchery.MOD_ID);
    //Creating an item
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));
    //tutorial items

    public static final RegistryObject<Item> CLAY_JAR = ITEMS.register("clay_jar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANOINTING_PASTE = ITEMS.register("anointing_paste",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WORMY_APPLE = ITEMS.register("wormy_apple",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARTICHOKE_BULB = ITEMS.register("artichoke_bulb",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_PROTECTION_POPPET = ITEMS.register("armor_protection_poppet",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATTUNED_STONE = ITEMS.register("attuned_stone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATTUNED_STONE_CHARGED = ITEMS.register("attuned_stone_charged",
            () -> new attuned_stone_charged(new Item.Properties()));
    public static final RegistryObject<Item> BALL_OF_BATS = ITEMS.register("ball_of_bats",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BONE_NEEDLE = ITEMS.register("bone_needle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIOME_NOTE = ITEMS.register("biome_note",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BELLADONNA_SEEDS = ITEMS.register("belladonna_seeds",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BELLADONNA_FLOWER = ITEMS.register("belladonna_flower",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEMON_HEART = ITEMS.register("demon_heart",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BREATH_OF_THE_GODDESS = ITEMS.register("breath_of_the_goddess",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOVEN_CRUOR = ITEMS.register("woven_cruor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CREEPER_HEART = ITEMS.register("creeper_heart",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHARM_OF_FANCIFUL_THINKING = ITEMS.register("charm_of_fanciful_thinking",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIRCLE_TALISMAN_EMPTY = ITEMS.register("circle_talisman_empty",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COOKED_LAMB = ITEMS.register("cooked_lamb",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLAY_JAR_UNFIRED = ITEMS.register("clay_jar_unfired",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDENSED_FEAR = ITEMS.register("condensed_fear",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BROOMSTICK = ITEMS.register("broomstick",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
