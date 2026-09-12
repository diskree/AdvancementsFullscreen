package io.github.diskria.advancements_fullscreen.client

import com.llamalad7.mixinextras.sugar.Local
import io.github.diskria.lapis.annotations.Env
import io.github.diskria.lapis.annotations.KMixin
import io.github.diskria.lapis.annotations.KShadow
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.advancements.AdvancementTab
import net.minecraft.client.gui.screens.advancements.AdvancementWidget
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.ModifyVariable
import javax.lang.model.element.Modifier.FINAL
import javax.lang.model.element.Modifier.PRIVATE

@KMixin(AdvancementWidget::class, Env.Client)
abstract class AdvancementWidgetMixin {

    private val advancementsScreen: AdvancementsScreen get() = tab.screen

    @ModifyVariable(
        method = ["extractHover(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIFII)V"],
        name = ["topSide"],
        at = [At(value = "STORE")]
    )
    fun fixHoverOutOfScreen(
        original: Boolean,
        @Local(name = ["titleTop"]) titleTop: Int,
        @Local(name = ["titleBarBottom"]) titleBarBottom: Int,
        @Local(name = ["descriptionTextHeight"]) descriptionTextHeight: Int,
        @Local(name = ["descriptionHeight"]) descriptionHeight: Int,
    ): Boolean = with(advancementsScreen) {
        val hoverBottom = titleBarBottom + descriptionHeight
        val hoverTop = titleTop - descriptionTextHeight + 1
        val backgroundTop = descriptionHeight - descriptionTextHeight
        val windowBottom = fullscreenBackgroundHeight + AdvancementsScreen.WINDOW_INSIDE_Y + fullscreenVerticalMargin
        val windowTop = -(AdvancementsScreen.WINDOW_INSIDE_X + fullscreenVerticalMargin)
        return when {
            hoverBottom < fullscreenBackgroundHeight -> false
            hoverTop >= backgroundTop -> true
            hoverBottom <= windowBottom -> false
            hoverTop >= windowTop -> true
            else -> Minecraft.getInstance().hasAltDown()
        }
    }

    @KShadow(PRIVATE, FINAL)
    abstract val tab: AdvancementTab
}
