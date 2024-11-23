package net.mqx.losttime;

import net.fabricmc.api.ModInitializer;
import net.mqx.losttime.item.ModItemGroups;
import net.mqx.losttime.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LostTime implements ModInitializer {
    public static final String MOD_ID = "losttime";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItemGroups.registerModItemGroups();
        ModItems.registerModItems();
    }
}