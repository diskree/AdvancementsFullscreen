package com.diskree.advancementsfullscreen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.texture.*;
import net.minecraft.resource.metadata.ResourceMetadata;
import org.jetbrains.annotations.NotNull;

public class FullscreenAdvancementsWindow extends Sprite {

    public static final int TOP_BORDER_WIDTH = 18;
    public static final int BORDER_WIDTH = 9;

    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    private static final int DIM_WIDTH = 6;

    private static final Scaling.NineSlice NINE_SLICE = new Scaling.NineSlice(
        AdvancementsScreen.WINDOW_WIDTH,
        AdvancementsScreen.WINDOW_HEIGHT,
        new Scaling.NineSlice.Border(
            BORDER_WIDTH + DIM_WIDTH,
            TOP_BORDER_WIDTH + DIM_WIDTH,
            BORDER_WIDTH + DIM_WIDTH,
            BORDER_WIDTH + DIM_WIDTH
        )
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

    public void draw(@NotNull DrawContext context, int screenWidth, int screenHeight) {
        context.drawSprite(
            this,
            NINE_SLICE,
            AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN,
            AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN,
            0,
            screenWidth - AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN * 2,
            screenHeight - AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN * 2
        );
    }
}
