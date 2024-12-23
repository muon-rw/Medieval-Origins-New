package dev.muon.medievalorigins.item;

import dev.muon.medievalorigins.MedievalOrigins;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    private static final List<String> ORIGINS = List.of(
            "alfiq", "arachnae", "banshee", "incubus", "dwarf",
            "wood_elf", "high_elf", "fae", "goblin", "gorgon",
            "moon_elf", "revenant", "ogre", "pixie",
            "plague_victim", "siren", "valkyrie", "yeti"
    );

    public static final Map<String, Item> PORTRAIT_ITEMS = new HashMap<>();

    private static Item registerPortrait(String origin) {
        Item portrait = Registry.register(
                BuiltInRegistries.ITEM,
                MedievalOrigins.loc(origin),
                new Item(new Item.Properties())
        );
        PORTRAIT_ITEMS.put(origin, portrait);
        return portrait;
    }

    public static void register() {
        ORIGINS.forEach(ModItems::registerPortrait);
    }
}