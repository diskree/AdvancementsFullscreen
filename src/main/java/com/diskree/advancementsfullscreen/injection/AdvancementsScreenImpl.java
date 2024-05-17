package com.diskree.advancementsfullscreen.injection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface AdvancementsScreenImpl {

    int advancementsfullscreen$getFullscreenWindowWidth(boolean isWithBorder);

    int advancementsfullscreen$getFullscreenWindowHeight(boolean isWithBorder);
}
