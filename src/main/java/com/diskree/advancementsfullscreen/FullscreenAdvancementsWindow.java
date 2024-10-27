package com.diskree.advancementsfullscreen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.*;
import net.minecraft.resource.metadata.ResourceMetadata;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class FullscreenAdvancementsWindow extends Sprite {

    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int SHADOW_OFFSET = 6;

    private static final Scaling.NineSlice NINE_SLICE = new Scaling.NineSlice(
        AdvancementsScreen.WINDOW_WIDTH,
        AdvancementsScreen.WINDOW_HEIGHT,
        new Scaling.NineSlice.Border(
            AdvancementsScreen.PAGE_OFFSET_X + SHADOW_OFFSET,
            AdvancementsScreen.PAGE_OFFSET_Y + SHADOW_OFFSET,
            AdvancementsScreen.PAGE_OFFSET_X + SHADOW_OFFSET,
            AdvancementsScreen.PAGE_OFFSET_X + SHADOW_OFFSET
        ),
        true
    );

    public FullscreenAdvancementsWindow() {
        super(
            AdvancementsScreen.WINDOW_TEXTURE,
            new SpriteContents(
                AdvancementsScreen.WINDOW_TEXTURE,
                new SpriteDimensions(AdvancementsScreen.WINDOW_WIDTH, AdvancementsScreen.WINDOW_HEIGHT),
                new NativeImage(TEXTURE_WIDTH, TEXTURE_HEIGHT, false),
                ResourceMetadata.NONE
            ),
            TEXTURE_WIDTH,
            TEXTURE_HEIGHT,
            0,
            0
        );
    }

    public void draw(
        @NotNull DrawContext context,
        Function<Identifier, RenderLayer> renderLayers,
        int windowX,
        int windowY,
        int windowWidth,
        int windowHeight
    ) {
        context.drawSpriteNineSliced(renderLayers, this, NINE_SLICE, windowX, windowY, windowWidth, windowHeight, -1);
    }
}
