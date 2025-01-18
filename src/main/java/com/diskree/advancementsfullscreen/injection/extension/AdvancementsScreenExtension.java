package com.diskree.advancementsfullscreen.injection.extension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public interface AdvancementsScreenExtension {
    int advancementsfullscreen$getWindowWidth(boolean isWithBorder);

    int advancementsfullscreen$getWindowHeight(boolean isWithBorder);

    int advancementsfullscreen$getWindowHorizontalMargin();

    int advancementsfullscreen$getWindowVerticalMargin();

    void advancementsfullscreen$resize(MinecraftClient client, int width, int height);
}
