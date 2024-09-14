package com.diskree.advancementsfullscreen.injection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface AdvancementsScreenImpl {

    int advancementsfullscreen$getWindowWidth(boolean isWithBorder);

    int advancementsfullscreen$getWindowHeight(boolean isWithBorder);

    int advancementsfullscreen$getWindowHorizontalMargin();

    int advancementsfullscreen$getWindowVerticalMargin();
}
