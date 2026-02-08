package net.tv337.witchery2.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.tv337.witchery2.util.ITickableBlockEntity;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class WitchCauldronBe extends BlockEntity implements ITickableBlockEntity {
    protected static final Set<Block> HEAT_SOURCES = Set.of(Blocks.FIRE, Blocks.CAMPFIRE, Blocks.SOUL_FIRE, Blocks.SOUL_CAMPFIRE, Blocks.LAVA);
    private final List<ItemStack> brewingItems = new ArrayList<>(); // Stores tossed items

    public WitchCauldronBe(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WITCH_CAULDRON.get(), pPos, pBlockState);
    }
    private boolean hasHeatSourceBelow() {
        BlockState stateBelow = level.getBlockState(worldPosition.below());
        return HEAT_SOURCES.contains(stateBelow.getBlock());
    }
    public void tick() {
        if (level == null || level.isClientSide) return;

        boolean hasHeatSource = this.hasHeatSourceBelow();
        if (!hasHeatSource) return;

        this.pickupItems();


    }
    private void pickupItems() {
        BlockPos abovePos = this.worldPosition.above();
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(abovePos));
        for (ItemEntity itemEntity : items) {
            ItemStack stack = itemEntity.getItem();
            if (!stack.isEmpty()) {
                brewingItems.add(stack.copy());
                itemEntity.remove(Entity.RemovalReason.KILLED);
            }
        }

    }

}
