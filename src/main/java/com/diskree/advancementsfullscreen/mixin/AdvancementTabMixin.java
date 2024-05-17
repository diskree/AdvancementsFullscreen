package com.diskree.advancementsfullscreen.mixin;

import com.diskree.advancementsfullscreen.injection.AdvancementsScreenImpl;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.PAGE_HEIGHT;
import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.PAGE_WIDTH;

@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {

    @Shadow
    @Final
    private AdvancementsScreen screen;

    @ModifyConstant(
        method = "move",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 0
        )
    )
    private int calculateMoveLimitByX(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "move",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateMoveLimitByY(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "move",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 1
        )
    )
    private int calculateMoveMinimumX(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "move",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 1
        )
    )
    private int calculateMoveMinimumY(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "drawWidgetTooltip",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 0
        )
    )
    private int calculateWidthForTooltipDim(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "drawWidgetTooltip",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 0
        )
    )
    private int calculateHeightForTooltipDim(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "drawWidgetTooltip",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 1
        )
    )
    private int calculateWidthForWidgetHoverCheck(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "drawWidgetTooltip",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 1
        )
    )
    private int calculateHeightForWidgetHoverCheck(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = PAGE_WIDTH,
            ordinal = 0
        )
    )
    private int drawFullscreenBackgroundByWidth(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 0
        )
    )
    private int drawFullscreenBackgroundByHeight(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(false);
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = PAGE_WIDTH / 2,
            ordinal = 0
        )
    )
    private int calculateWidthForOriginX(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(false) / 2;
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = PAGE_HEIGHT / 2,
            ordinal = 0
        )
    )
    private int calculateHeightForOriginY(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(false) / 2;
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = 15,
            ordinal = 0
        )
    )
    private int calculateWidthForBackgroundGridColumnsCount(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(true) / 16 + 1;
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "render",
        constant = @Constant(
            intValue = 8,
            ordinal = 0
        )
    )
    private int calculateHeightForBackgroundGridRowsCount(int originalValue) {
        if (screen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(true) / 16 + 1;
        }
        return originalValue;
    }
}
