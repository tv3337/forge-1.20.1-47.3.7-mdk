package net.tv337.witchery2.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AttunedStoneCharged extends Item {
    public AttunedStoneCharged(Properties properties){super(properties);}

    @Override
    public boolean isFoil(ItemStack stack){
        return true;
    }
}
