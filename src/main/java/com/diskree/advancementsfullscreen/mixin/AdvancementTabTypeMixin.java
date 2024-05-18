package com.diskree.advancementsfullscreen.mixin;

import com.diskree.advancementsfullscreen.injection.AdvancementsScreenImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.WINDOW_HEIGHT;
import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.WINDOW_WIDTH;

@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {

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

    @Redirect(
        method = "drawBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementTabType$Textures;last()Lnet/minecraft/util/Identifier;"
        )
    )
    public Identifier drawMiddleBackgroundInsteadLast(@NotNull AdvancementTabType.Textures textures) {
        return textures.middle();
    }
}
