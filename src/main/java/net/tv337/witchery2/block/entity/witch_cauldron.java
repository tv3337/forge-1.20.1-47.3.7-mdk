package net.tv337.witchery2.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class witch_cauldron extends BlockEntity implements MenuProvider {
    public witch_cauldron(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WITCH_CAULDRON.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Witches Cauldron");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
