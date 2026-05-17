package net.tv337.witchery2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.tv337.witchery2.block.entity.WitchCauldronBe;
import net.tv337.witchery2.util.CauldronUtils;
import net.tv337.witchery2.util.WitchCauldronInteraction;


import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


public class WitchCauldron extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(1, 0,1, 15, 13, 15);
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 3);
    public static final BooleanProperty BUBBLING = BooleanProperty.create("bubbling");
    protected final Map<Item, WitchCauldronInteraction> interactions;
    public static final EnumProperty<CauldronWaterColor> WATER_COLOR = EnumProperty.create("water_color",
            CauldronWaterColor.class);
    private static final VoxelShape INSIDE = Block.box(2, 3, 2, 11, 10, 11);

    //generation of the block entity
    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WitchCauldronBe(pPos, pState);
    }

    //initial setup
    public enum CauldronWaterColor implements StringRepresentable {
        DEFAULT("default"), FAIL("fail"), SUCCESS("success");

        private final String name;

        CauldronWaterColor(String name) {
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

    //initial setup and generation of the block
    public WitchCauldron(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(BUBBLING, false)
                .setValue(LEVEL, 0).setValue(WATER_COLOR, CauldronWaterColor.DEFAULT)
                .setValue(FACING, Direction.NORTH));
        this.interactions = new HashMap<>();
    }

    //initial setup
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    //initial setup
    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return INSIDE;
    }

    //initial setup
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    //initial setup
    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext){
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    //initial setup
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BUBBLING, LEVEL, WATER_COLOR, FACING);
    }

    //setup if generated in a structure
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    //setup if generated in a structure
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    //enables bubbling if placed above a heat source
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {

        if (!level.isClientSide) {

            boolean heated = hasHeatSource(level, pos);

            level.setBlock(
                    pos,
                    state.setValue(BUBBLING, heated),
                    3
            );
        }

        super.onPlace(state, level, pos, oldState, isMoving);
    }

    //handles dealing with bucket filling and emptying
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {


        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == Items.BUCKET) {

            int currentLevel = state.getValue(LEVEL);
            CauldronWaterColor color = state.getValue(WATER_COLOR);

            // Only allow removing normal water
            if (currentLevel > 0 && color == CauldronWaterColor.DEFAULT) {

                if (!level.isClientSide) {

                    CauldronUtils.setCauldronColor(level, pos, 0xAA33FF);

                    // Decrease level
                    level.setBlock(
                            pos,
                            state.setValue(LEVEL, currentLevel - 1),
                            3
                    );

                    // Give water bucket
                    if (!player.getAbilities().instabuild) {
                        player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                    }

                    // Sound
                    level.playSound(
                            null,
                            pos,
                            SoundEvents.BUCKET_FILL,
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            return InteractionResult.FAIL;
        }
        // Water bucket interaction
        if (stack.getItem() == Items.WATER_BUCKET) {

            int currentLevel = state.getValue(LEVEL);

            // Prevent overfilling
            if (currentLevel < 3) {

                if (!level.isClientSide) {

                    // Increase water level
                    level.setBlock(
                            pos,
                            state.setValue(LEVEL, currentLevel + 1),
                            3
                    );

                    // Replace water bucket with empty bucket
                    if (!player.getAbilities().instabuild) {
                        player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    }

                    // Play sound
                    level.playSound(
                            null,
                            pos,
                            SoundEvents.BUCKET_EMPTY,
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    //Checks for heat source below when updated
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (!level.isClientSide) {

            boolean heated = hasHeatSource(level, pos);

            if (state.getValue(BUBBLING) != heated) {

                level.setBlock(
                        pos,
                        state.setValue(BUBBLING, heated),
                        3
                );
            }
        }

        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    //returns for a heat source below
    private boolean hasHeatSource(Level level, BlockPos pos) {

        BlockPos belowPos = pos.below();

        return level.getBlockState(belowPos)
                .is(net.tv337.witchery2.util.ModTags.Blocks.CAULDRON_HEAT_SOURCES);
    }

    //animates bubbles if heated
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

        if (!state.getValue(BUBBLING)) {
            return;
        }

        BlockEntity be = level.getBlockEntity(pos);

        int color = 0xff0000;

        if (be instanceof WitchCauldronBe cauldron) {
            color = cauldron.getWaterColor();
        }

        float r = ((color >> 16) & 255) / 255.0F;
        float g = ((color >> 8) & 255) / 255.0F;
        float b = (color & 255) / 255.0F;



        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.7;
        double z = pos.getZ() + 0.5;

        for (int i = 0; i < 3; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 0.6;
            double offsetZ = (random.nextDouble() - 0.5) * 0.6;
            level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offsetX, y,z + offsetZ, r,g,b);
        }

        if (random.nextInt(10) == 0) {
            level.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS,
                    0.3F, 0.8F + random.nextFloat() * 0.4F, false);
        }
    }

}
