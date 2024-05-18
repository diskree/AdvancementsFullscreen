package com.diskree.advancementsfullscreen.mixin;

import com.diskree.advancementsfullscreen.AdvancementsFullscreen;
import com.diskree.advancementsfullscreen.FullscreenAdvancementsWindow;
import com.diskree.advancementsfullscreen.injection.AdvancementsScreenImpl;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.*;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin extends Screen implements AdvancementsScreenImpl {

    @Unique
    private final FullscreenAdvancementsWindow fullscreenAdvancementsWindow = new FullscreenAdvancementsWindow();

    public AdvancementsScreenMixin() {
        super(null);
    }

    @Override
    public int advancementsfullscreen$getFullscreenWindowWidth(boolean isWithBorder) {
        int result = width - AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN * 2;
        if (!isWithBorder) {
            result -= (PAGE_OFFSET_X * 2);
        }
        return result;
    }

    @Override
    public int advancementsfullscreen$getFullscreenWindowHeight(boolean isWithBorder) {
        int result = height - AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN * 2;
        if (!isWithBorder) {
            result -= (PAGE_OFFSET_Y + PAGE_OFFSET_X);
        }
        return result;
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        tabs.values().forEach((tab) -> tab.initialized = false);
    }

    @Shadow
    @Final
    public static int PAGE_OFFSET_X;

    @Shadow
    @Final
    public static int PAGE_OFFSET_Y;

    @Shadow
    @Final
    private Map<AdvancementEntry, AdvancementTab> tabs;

    @Redirect(
        method = "drawWindow",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
            ordinal = 0
        )
    )
    public void drawFullscreenWindow(
        DrawContext context,
        Identifier texture,
        int x,
        int y,
        int u,
        int v,
        int w,
        int h
    ) {
        fullscreenAdvancementsWindow.draw(context, width, height);
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = WINDOW_WIDTH,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenWidthOnRender(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowWidth(true);
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = WINDOW_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenHeightOnRender(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowHeight(true);
    }

    @ModifyConstant(
        method = "mouseClicked",
        constant = @Constant(
            intValue = WINDOW_WIDTH,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenWidthOnMouseClicked(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowWidth(true);
    }

    @ModifyConstant(
        method = "mouseClicked",
        constant = @Constant(
            intValue = WINDOW_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenHeightOnMouseClicked(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowHeight(true);
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 0
        )
    )
    private int calculateWidthOfEmptyBlackBackground(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowWidth(false);
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHeightOfEmptyBlackBackground(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowHeight(false);
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_WIDTH / 2,
            ordinal = 0
        )
    )
    private int moveEmptyTextAndSadLabelTextToCenterOfWidth(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowWidth(false) / 2;
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_HEIGHT / 2,
            ordinal = 0
        )
    )
    private int moveEmptyTextToCenterOfHeight(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowHeight(false) / 2;
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 1
        )
    )
    private int moveSadLabelTextToBottom(int originalValue) {
        return advancementsfullscreen$getFullscreenWindowHeight(false);
    }
}
