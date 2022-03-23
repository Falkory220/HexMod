package at.petrak.hexcasting.client.gui;

import at.petrak.hexcasting.client.ClientTickCounter;
import at.petrak.hexcasting.client.RenderLib;
import at.petrak.hexcasting.hexmath.HexPattern;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.phys.Vec2;

import java.util.List;
import java.util.stream.Collectors;

// https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/client/tooltip/MapTooltips.java
// yoink
public class PatternTooltipGreeble implements ClientTooltipComponent, TooltipComponent {
    public static final ResourceLocation PRISTINE_BG = new ResourceLocation(
        "hexcasting:textures/gui/scroll.png");
    public static final ResourceLocation ANCIENT_BG = new ResourceLocation(
        "hexcasting:textures/gui/scroll_ancient.png");
    public static final ResourceLocation SLATE_BG = new ResourceLocation(
        "hexcasting:textures/gui/slate.png");

    private static final float SIZE = 72f;

    private final HexPattern pattern;
    private final List<Vec2> zappyPoints;
    private final List<Vec2> pathfinderDots;
    private final float scale;
    private final ResourceLocation background;

    public PatternTooltipGreeble(HexPattern pattern, ResourceLocation background) {
        this.pattern = pattern;
        this.background = background;
        // Do two passes: one with a random size to find a good COM and one with the real calculation
        var com1 = this.pattern.getCenter(1);
        var lines1 = this.pattern.toLines(1, Vec2.ZERO);

        var maxDx = -1f;
        var maxDy = -1f;
        for (var dot : lines1) {
            var dx = Mth.abs(dot.x - com1.x);
            if (dx > maxDx) {
                maxDx = dx;
            }
            var dy = Mth.abs(dot.y - com1.y);
            if (dy > maxDy) {
                maxDy = dy;
            }
        }
        this.scale = Math.min(8f, Math.min(SIZE / 3f / maxDx, SIZE / 3f / maxDy));

        var com2 = this.pattern.getCenter(this.scale);
        var lines2 = this.pattern.toLines(this.scale, com2.negated());
        this.zappyPoints = RenderLib.makeZappy(lines2, 10f, 0.8f, 0f);
        this.pathfinderDots = lines2.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void renderImage(Font font, int mouseX, int mouseY, PoseStack ps, ItemRenderer pItemRenderer,
        int pBlitOffset) {
        var width = this.getWidth(font);
        var height = this.getHeight();

        // far as i can tell "mouseX" and "mouseY" are actually the positions of the corner of the tooltip
        ps.pushPose();
        ps.translate(mouseX, mouseY, 500);
        RenderSystem.enableBlend();
        renderBG(ps, this.background);

        // renderText happens *before* renderImage for some asinine reason
//                RenderSystem.disableBlend();
        ps.translate(0, 0, 100);

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableCull();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        ps.translate(width / 2f, height / 2f, 1);

        var mat = ps.last().pose();
        var outer = 0xff_d2c8c8;
        var innerLight = 0xc8_aba2a2;
        var innerDark = 0xc8_322b33;
        RenderLib.drawLineSeq(mat, this.zappyPoints, 5f, 0,
            outer, outer, null);
        RenderLib.drawLineSeq(mat, this.zappyPoints, 2f, 0,
            innerDark, innerLight,
            ClientTickCounter.getTickCount() / 40f);
        RenderLib.drawSpot(mat, this.zappyPoints.get(0), 2.5f, 1f, 0.1f, 0.15f, 0.6f);

        for (var dot : this.pathfinderDots) {
            RenderLib.drawSpot(mat, dot, 1.5f, 0.82f, 0.8f, 0.8f, 0.5f);
        }

        ps.popPose();
    }

    private static void renderBG(PoseStack ps, ResourceLocation background) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, background);


        // i wish i liked mobius front enough ot get to the TIS puzzles
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        Matrix4f neo = ps.last().pose();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(neo, 0, 0, 0.0F).uv(0.0F, 0.0F).endVertex();
        buffer.vertex(neo, 0, SIZE, 0.0F).uv(0.0F, 1.0f).endVertex();
        buffer.vertex(neo, SIZE, SIZE, 0.0F).uv(1.0F, 1.0f).endVertex();
        buffer.vertex(neo, SIZE, 0, 0.0F).uv(1.0F, 0.0F).endVertex();
        buffer.end();
        BufferUploader.end(buffer);


    }

    @Override
    public int getWidth(Font pFont) {
        return (int) SIZE;
    }

    @Override
    public int getHeight() {
        return (int) SIZE;
    }
}
