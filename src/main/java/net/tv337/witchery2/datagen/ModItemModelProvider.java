package net.tv337.witchery2.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.tv337.witchery2.*;
import net.tv337.witchery2.item.*;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Witchery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.ANOINTING_PASTE);
        simpleItem(ModItems.ARMOR_PROTECTION_POPPET);
        simpleItem(ModItems.ARTICHOKE_BULB);
        simpleItem(ModItems.ATTUNED_STONE);
        simpleItem(ModItems.ATTUNED_STONE_CHARGED);
        simpleItem(ModItems.BALL_OF_BATS);
        simpleItem(ModItems.BELLADONNA_FLOWER);
        simpleItem(ModItems.BELLADONNA_SEEDS);
        simpleItem(ModItems.BIOME_NOTE);
        simpleItem(ModItems.BONE_NEEDLE);
        simpleItem(ModItems.BREATH_OF_THE_GODDESS);
        simpleItem(ModItems.CHARM_OF_FANCIFUL_THINKING);
        simpleItem(ModItems.CIRCLE_TALISMAN_EMPTY);
        simpleItem(ModItems.CLAY_JAR);
        simpleItem(ModItems.CLAY_JAR_UNFIRED);
        simpleItem(ModItems.COOKED_LAMB);
        simpleItem(ModItems.DEMON_HEART);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Witchery.MOD_ID, "item/" + item.getId().getPath()));
    }
}
