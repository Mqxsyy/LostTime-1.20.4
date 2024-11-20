package net.mqx.losttime.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mqx.losttime.LostTime;

public class ModItemGroups {
    public static final ItemGroup LOSTTIME_GROUP= Registry.register(
            Registries.ITEM_GROUP, new Identifier(LostTime.MOD_ID, "losttime"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.losttime"))
                    .icon(() -> new ItemStack(ModItems.ICE_SHARD))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.ICE_SHARD);
                    })).build());

    public static void registerModItemGroups() {
        LostTime.LOGGER.info("Registering Mod Item Groups for " + LostTime.MOD_ID);
    }
}
