package net.tv337.witchery2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.tv337.witchery2.block.custom.WitchCauldron;
import net.tv337.witchery2.block.entity.WitchCauldronBe;


public class WitchCauldronRenderer implements BlockEntityRenderer<WitchCauldronBe> {
    public static final ResourceLocation WATER_STILL = new ResourceLocation("minecraft:block/water_still");

    public WitchCauldronRenderer(BlockEntityRendererProvider.Context context) {
        // THIS
    }

    @Override
    public void render(WitchCauldronBe pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pLight, int pOverlay) {

        BlockState state = pBlockEntity.getBlockState();
        int level = state.getValue(WitchCauldron.LEVEL);
        String cauldronWaterColor = state.getValue(WitchCauldron.WATER_COLOR).toString();

        if (level == 0) {
            return;
        }

        float r = 0.2f;
        float g = 0.4f;
        float b = 1.0f;

        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(WATER_STILL);

        float[] heightByLevel = {
                0.0f,   // level 0 (unused)
                0.25f,  // level 1
                0.50f,  // level 2
                0.75f   // level 3
        };

        float height = heightByLevel[level];
        float inset = 0.125f;
        float size = 1.0f - inset * 2;
        pPoseStack.pushPose();
        pPoseStack.translate(inset, height, inset);
        VertexConsumer builder = pBuffer.getBuffer(RenderType.translucent());

        builder.vertex(pPoseStack.last().pose(), 0, 0, 0)
                .color(r, g, b, 1f)
                .uv(sprite.getU0(), sprite.getV0())
                .uv2(pLight)
                .overlayCoords(pOverlay)
                .normal(0, 1, 0)
                .endVertex();

        builder.vertex(pPoseStack.last().pose(), size, 0, 0)
                .color(r, g, b, 1f)
                .uv(sprite.getU1(), sprite.getV0())
                .uv2(pLight)
                .overlayCoords(pOverlay)
                .normal(0, 1, 0)
                .endVertex();

        builder.vertex(pPoseStack.last().pose(), size, 0, size)
                .color(r, g, b, 1f)
                .uv(sprite.getU1(), sprite.getV1())
                .uv2(pLight)
                .overlayCoords(pOverlay)
                .normal(0, 1, 0)
                .endVertex();

        builder.vertex(pPoseStack.last().pose(), 0, 0, size)
                .color(r, g, b, 1f)
                .uv(sprite.getU0(), sprite.getV1())
                .uv2(pLight)
                .overlayCoords(pOverlay)
                .normal(0, 1, 0)
                .endVertex();
        pPoseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(WitchCauldronBe pBlockEntity) {
        return true;
    }
}