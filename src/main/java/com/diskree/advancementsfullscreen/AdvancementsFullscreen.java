package com.diskree.advancementsfullscreen;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;

public class AdvancementsFullscreen implements ClientModInitializer {

    public static final int WINDOW_WIDTH = AdvancementsScreen.field_32298;
    public static final int WINDOW_HEIGHT = AdvancementsScreen.field_32299;
    public static final int PAGE_OFFSET_X = AdvancementsScreen.field_32306;
    public static final int PAGE_OFFSET_Y = AdvancementsScreen.field_32307;
    public static final int PAGE_WIDTH = AdvancementsScreen.field_32300;
    public static final int PAGE_HEIGHT = AdvancementsScreen.field_32301;

    public static final int ADVANCEMENTS_SCREEN_MARGIN = 30;

    @Override
    public void onInitializeClient() {
    }
}
