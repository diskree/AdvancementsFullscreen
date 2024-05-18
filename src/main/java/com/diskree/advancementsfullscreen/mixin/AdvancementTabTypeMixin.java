package com.diskree.advancementsfullscreen.mixin;

import com.diskree.advancementsfullscreen.AdvancementsFullscreen;
import com.diskree.advancementsfullscreen.injection.AdvancementsScreenImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.WINDOW_HEIGHT;
import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.WINDOW_WIDTH;

@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {

    @Shadow
    @Final
    private int u;

    @Shadow
    @Final
    private int width;

    @ModifyConstant(
        method = "getTabX",
        constant = @Constant(
            intValue = WINDOW_WIDTH - 4,
            ordinal = 0
        )
    )
    public int calculateTabXForFullscreenAtRightTabType(int originalValue) {
        if (MinecraftClient.getInstance().currentScreen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowWidth(true) - 4;
        }
        return originalValue;
    }

    @ModifyConstant(
        method = "getTabY",
        constant = @Constant(
            intValue = WINDOW_HEIGHT - 4,
            ordinal = 0
        )
    )
    public int calculateTabYForFullscreenAtBelowTabType(int originalValue) {
        if (MinecraftClient.getInstance().currentScreen instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getFullscreenWindowHeight(true) - 4;
        }
        return originalValue;
    }

    @ModifyArgs(
        method = "drawBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
        )
    )
    public void drawMiddleBackgroundInsteadLast(@NotNull Args args) {
        if (MinecraftClient.getInstance().currentScreen instanceof AdvancementsScreenImpl screenImpl) {
            int textureU = args.get(3);
            if (textureU > u) {
                AdvancementTabType tabType = (AdvancementTabType) (Object) this;
                int windowRight = AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN +
                    screenImpl.advancementsfullscreen$getFullscreenWindowWidth(true);
                int windowBottom = AdvancementsFullscreen.ADVANCEMENTS_SCREEN_MARGIN +
                    screenImpl.advancementsfullscreen$getFullscreenWindowHeight(true);
                int tabRight = (int) args.get(1) + (int) args.get(5);
                int tabBottom = (int) args.get(2) + (int) args.get(6);
                boolean isConnectedTextures =
                    tabType == AdvancementTabType.ABOVE || tabType == AdvancementTabType.BELOW ? tabRight == windowRight
                        : tabBottom == windowBottom;
                args.set(3, u + width * (isConnectedTextures ? 2 : 1));
            }
        }
    }
}
