package com.diskree.advancementsfullscreen.injection.mixin;

import com.diskree.advancementsfullscreen.injection.extension.AdvancementsScreenExtension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(
        method = "resize",
        at = @At(value = "HEAD")
    )
    private void resizeInAdvancementsScreen(MinecraftClient client, int width, int height, CallbackInfo ci) {
        Screen screen = (Screen) (Object) this;
        if (screen instanceof AdvancementsScreenExtension advancementsScreenExtension) {
            advancementsScreenExtension.advancementsfullscreen$resize(client, width, height);
        }
    }
}
