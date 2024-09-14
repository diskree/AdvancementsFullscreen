package com.diskree.advancementsfullscreen.mixin;

import com.diskree.advancementsfullscreen.injection.AdvancementsScreenImpl;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static net.minecraft.client.gui.screen.advancement.AdvancementsScreen.PAGE_HEIGHT;

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin {

    @Shadow
    @Final
    private AdvancementTab tab;

    @ModifyConstant(
        method = "drawTooltip",
        constant = @Constant(
            intValue = PAGE_HEIGHT,
            ordinal = 0
        )
    )
    public int drawTooltipModifyHeight(int originalValue) {
        if (tab.getScreen() instanceof AdvancementsScreenImpl screenImpl) {
            return screenImpl.advancementsfullscreen$getWindowHeight(false);
        }
        return originalValue;
    }
}
