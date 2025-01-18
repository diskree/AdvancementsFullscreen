package com.diskree.advancementsfullscreen.injection.mixin;

import com.diskree.advancementsfullscreen.AdvancementsFullscreenMod;
import com.diskree.advancementsfullscreen.FullscreenAdvancementsWindow;
import com.diskree.advancementsfullscreen.injection.extension.AdvancementsScreenExtension;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.*;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin extends Screen implements AdvancementsScreenExtension {

    @Unique
    private final FullscreenAdvancementsWindow fullscreenAdvancementsWindow = new FullscreenAdvancementsWindow();

    @Unique
    private int windowWidth;

    @Unique
    private int windowHeight;

    @Unique
    private int windowHorizontalMargin;

    @Unique
    private int windowVerticalMargin;

    public AdvancementsScreenMixin() {
        super(null);
    }

    @Override
    public int advancementsfullscreen$getWindowWidth(boolean isWithBorder) {
        return isWithBorder ? windowWidth : windowWidth - (PAGE_OFFSET_X * 2);
    }

    @Override
    public int advancementsfullscreen$getWindowHeight(boolean isWithBorder) {
        return isWithBorder ? windowHeight : windowHeight - (PAGE_OFFSET_Y + PAGE_OFFSET_X);
    }

    @Override
    public int advancementsfullscreen$getWindowHorizontalMargin() {
        return windowHorizontalMargin;
    }

    @Override
    public int advancementsfullscreen$getWindowVerticalMargin() {
        return windowVerticalMargin;
    }

    @Override
    public void advancementsfullscreen$resize(MinecraftClient client, int width, int height) {
        tabs.values().forEach((tab) -> tab.initialized = false);
        calculateWindowSizeAndPosition(width, height);
    }

    @Unique
    private void calculateWindowSizeAndPosition(int screenWidth, int screenHeight) {
        int tabSize = AdvancementTabType.ABOVE.width;

        int tabsHorizontalSpacing = AdvancementTabType.ABOVE.getTabX(1) - tabSize;
        int availableScreenWidth = screenWidth - AdvancementsFullscreenMod.ADVANCEMENTS_SCREEN_MINIMUM_MARGIN * 2;
        int maxWindowWidth = 0;
        int horizontalTabsCount = 1;
        while (true) {
            int requiredWidth = horizontalTabsCount * tabSize + (horizontalTabsCount - 1) * tabsHorizontalSpacing;
            if (requiredWidth > availableScreenWidth) {
                break;
            }
            maxWindowWidth = requiredWidth;
            horizontalTabsCount++;
        }
        windowWidth = maxWindowWidth;
        windowHorizontalMargin = (screenWidth - windowWidth) / 2;

        int availableScreenHeight = screenHeight - AdvancementsFullscreenMod.ADVANCEMENTS_SCREEN_MINIMUM_MARGIN * 2;
        int maxWindowHeight = 0;
        int verticalTabsCount = 1;
        while (true) {
            int requiredHeight = verticalTabsCount * tabSize;
            if (requiredHeight > availableScreenHeight) {
                break;
            }
            maxWindowHeight = requiredHeight;
            verticalTabsCount++;
        }
        windowHeight = maxWindowHeight;
        windowVerticalMargin = (screenHeight - windowHeight) / 2;
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
            target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIFFIIII)V",
            ordinal = 0
        )
    )
    public void drawFullscreenWindow(
        DrawContext context,
        Function<Identifier, RenderLayer> renderLayers,
        Identifier sprite,
        int x,
        int y,
        float u,
        float v,
        int width,
        int height,
        int textureWidth,
        int textureHeight
    ) {
        fullscreenAdvancementsWindow.draw(
            context,
            renderLayers,
            windowHorizontalMargin,
            windowVerticalMargin,
            windowWidth,
            windowHeight
        );
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = WINDOW_WIDTH,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenWidthOnRender(int originalValue) {
        return advancementsfullscreen$getWindowWidth(true);
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = WINDOW_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenHeightOnRender(int originalValue) {
        return advancementsfullscreen$getWindowHeight(true);
    }

    @ModifyConstant(
        method = "mouseClicked",
        constant = @Constant(
            intValue = WINDOW_WIDTH,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenWidthOnMouseClicked(int originalValue) {
        return advancementsfullscreen$getWindowWidth(true);
    }

    @ModifyConstant(
        method = "mouseClicked",
        constant = @Constant(
            intValue = WINDOW_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHalfOfScreenHeightOnMouseClicked(int originalValue) {
        return advancementsfullscreen$getWindowHeight(true);
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 0
        )
    )
    private int calculateWidthOfEmptyBlackBackground(int originalValue) {
        return advancementsfullscreen$getWindowWidth(false);
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHeightOfEmptyBlackBackground(int originalValue) {
        return advancementsfullscreen$getWindowHeight(false);
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_WIDTH / 2,
            ordinal = 0
        )
    )
    private int moveEmptyTextAndSadLabelTextToCenterOfWidth(int originalValue) {
        return advancementsfullscreen$getWindowWidth(false) / 2;
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_HEIGHT / 2,
            ordinal = 0
        )
    )
    private int moveEmptyTextToCenterOfHeight(int originalValue) {
        return advancementsfullscreen$getWindowHeight(false) / 2;
    }

    @ModifyConstant(
        method = "drawAdvancementTree",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 1
        )
    )
    private int moveSadLabelTextToBottom(int originalValue) {
        return advancementsfullscreen$getWindowHeight(false);
    }

    @Redirect(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addHeader(Lnet/minecraft/text/Text;Lnet/minecraft/client/font/TextRenderer;)V",
            ordinal = 0
        )
    )
    private void removeHeader(ThreePartsLayoutWidget layout, Text text, TextRenderer textRenderer) {
    }

    @Redirect(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addFooter(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;",
            ordinal = 0
        )
    )
    private <T extends Widget> @Nullable T removeFooter(ThreePartsLayoutWidget layout, T widget) {
        return null;
    }

    @Redirect(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;forEachChild(Ljava/util/function/Consumer;)V",
            ordinal = 0
        )
    )
    private void cancelAddDrawableChild(ThreePartsLayoutWidget layout, Consumer<ClickableWidget> consumer) {
    }

    @Inject(
        method = "init",
        at = @At(value = "RETURN")
    )
    private void calculateWindowSizeAndPositionOnInit(CallbackInfo ci) {
        calculateWindowSizeAndPosition(width, height);
    }
}
