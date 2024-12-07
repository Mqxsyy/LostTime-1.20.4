package net.mqx.losttime.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mqx.losttime.LostTime;
import net.mqx.losttime.item.cosmetic.CosmeticItem;
import net.mqx.losttime.item.cosmetic.HaloCosmeticItem;

public class ModItems {
    public static final Item ICE_SHARD = registerItem("ice_shard", new Item(new FabricItemSettings()));
    public static final Item ICE_WAND = registerItem("ice_wand", new IceWandItem(new FabricItemSettings()));

    public static final CosmeticItem HALO = (CosmeticItem) registerItem("halo", new HaloCosmeticItem());

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(ICE_SHARD);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(LostTime.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LostTime.LOGGER.info("Registering Mod Items for " + LostTime.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
