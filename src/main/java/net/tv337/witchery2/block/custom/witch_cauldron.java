package net.tv337.witchery2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.tv337.witchery2.block.ModBlocks;
import net.tv337.witchery2.block.entity.witch_cauldron_be;
import net.tv337.witchery2.util.ITickableBlockEntity;
import net.tv337.witchery2.util.WitchCauldronInteraction;
import com.google.common.base.Predicate;


import javax.annotation.Nullable;
import java.util.Map;


public class witch_cauldron extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(1, 0,1, 15, 13, 15);
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 3);
    public static final BooleanProperty BUBBLING = BooleanProperty.create("bubbling");
    protected final Map<Item, WitchCauldronInteraction> interactions;
    public static final EnumProperty<CauldronWaterColor> WATER_COLOR = EnumProperty.create("water_color",
            CauldronWaterColor.class);
    private static final VoxelShape INSIDE = Block.box(2, 3, 2, 11, 10, 11);

    public enum CauldronWaterColor implements StringRepresentable {
        DEFAULT("default"), FAIL("fail"), SUCCESS("success");

        private final String name;

        private CauldronWaterColor(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public witch_cauldron(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(BUBBLING, Boolean.valueOf(false))
                .setValue(LEVEL, Integer.valueOf(0)).setValue(WATER_COLOR, CauldronWaterColor.DEFAULT));
        this.interactions = null;
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return INSIDE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext){
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BUBBLING, LEVEL, WATER_COLOR, FACING);
    }

    public BlockState updatedState(BlockState state, int addition) {
        int currentLevelValue = state.getValue(LEVEL);
        int updateLevelValue = Math.max(0, Math.min(3, currentLevelValue + addition));
        return state.setValue(LEVEL, Integer.valueOf(updateLevelValue));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        ItemStack heldItemStack = pPlayer.getItemInHand(pHand);
        if (pLevel.isClientSide())
            return InteractionResult.sidedSuccess(pLevel.isClientSide());

        this.onUseWithWaterBottle(heldItemStack, pLevel, pPos, pPlayer, pHand);
        this.onUseWithGlassBottle(heldItemStack, pLevel, pPos, pPlayer, pHand);
        this.onUseWithEmptyBucket(heldItemStack, pLevel, pPos, pPlayer, pHand);
        this.onUseWithFilledBucket(heldItemStack, pLevel, pPos, pPlayer, pHand);


        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }


    //Interaction results
    private InteractionResult emptyBucket(BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, ItemStack filledStack, SoundEvent fillSound) {
        player.setItemInHand(hand, ItemUtils.createFilledResult(filledStack, player, new ItemStack(Items.BUCKET)));
        level.setBlockAndUpdate(pos, this.updatedState(state, 3));
        level.playSound((ServerPlayer) null, pos, fillSound, SoundSource.BLOCKS);
        level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
        return InteractionResult.CONSUME;
    }

    private InteractionResult fillBucket(BlockState state, Level level, BlockPos pos, Player player,
                                         InteractionHand hand, ItemStack emptyStack, ItemStack filledStack, Predicate<BlockState> statePredicate,
                                         SoundEvent fillSound) {
        if (!statePredicate.test(state))
            return InteractionResult.PASS;

        player.setItemInHand(hand, ItemUtils.createFilledResult(emptyStack, player, filledStack));
        level.setBlockAndUpdate(pos, ModBlocks.WITCH_CAULDRON.get().defaultBlockState());
        level.playSound((ServerPlayer) null, pos, fillSound, SoundSource.BLOCKS);
        level.gameEvent((Entity) null, GameEvent.FLUID_PICKUP, pos);
        return InteractionResult.CONSUME;
    }

    private InteractionResult onUseWithEmptyBucket(ItemStack stack, Level level, BlockPos pos, Player player,
                                                   InteractionHand hand) {
        if (stack.getItem() == Items.BUCKET) {
            BlockState currentState = level.getBlockState(pos);
            this.fillBucket(currentState, level, pos, player, hand, stack, new ItemStack(Items.WATER_BUCKET),
                    state -> state.getValue(LEVEL) == 3, SoundEvents.BUCKET_FILL);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult onUseWithFilledBucket(ItemStack stack, Level level, BlockPos pos, Player player,
                                                    InteractionHand hand) {
        BlockState currentBlockState = level.getBlockState(pos);
        if (stack.getItem() == Items.WATER_BUCKET && currentBlockState.getValue(LEVEL) != 3) {
            this.emptyBucket(currentBlockState, level, pos, player, hand, stack, SoundEvents.BUCKET_EMPTY);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult onUseWithWaterBottle(ItemStack stack, Level level, BlockPos pos, Player player,
                                                   InteractionHand hand) {
        if (stack.getItem() == Items.POTION) {
            if (PotionUtils.getPotion(stack) != Potions.WATER)
                return InteractionResult.PASS;

            BlockState currentState = level.getBlockState(pos);
            if (currentState.getValue(LEVEL) >= 3)
                return InteractionResult.PASS;

            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));

            level.setBlockAndUpdate(pos, this.updatedState(currentState, 1));
            level.playSound((ServerPlayer) null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS);
            level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult onUseWithGlassBottle(ItemStack stack, Level level, BlockPos pos, Player player,
                                                   InteractionHand hand) {
        if (stack.getItem() == Items.GLASS_BOTTLE) {
            BlockState currentState = level.getBlockState(pos);
            if (currentState.getValue(LEVEL) <= 0)
                return InteractionResult.PASS;

            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player,
                    PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)));

            level.setBlock(pos, this.updatedState(currentState, -1), 3);
            level.setBlockAndUpdate(pos, this.updatedState(currentState, -1));
            level.playSound((ServerPlayer) null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public void lowerFillLevel(Level level, BlockPos pos) {
        BlockState currentState = level.getBlockState(pos);
        if (currentState.getValue(LEVEL) <= 0)
            return;
        level.setBlockAndUpdate(pos, this.updatedState(currentState, -1));
    }

    protected double getContentHeight(BlockState state) {
        return (6.0 + (double) state.getValue(LEVEL).intValue() * 3.0) / 16.0;
    }

    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == 3;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new witch_cauldron_be(pPos,pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
                                                                  BlockEntityType<T> pBlockEntityType) {
        return ITickableBlockEntity.getTickerHelper(pLevel);
    }

}
