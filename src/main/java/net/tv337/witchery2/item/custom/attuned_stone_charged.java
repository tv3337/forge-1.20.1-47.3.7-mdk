package net.tv337.witchery2.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class attuned_stone_charged extends Item {
    public attuned_stone_charged(Properties properties){super(properties);}

    @Override
    public boolean isFoil(ItemStack stack){
        return true;
    }
}
