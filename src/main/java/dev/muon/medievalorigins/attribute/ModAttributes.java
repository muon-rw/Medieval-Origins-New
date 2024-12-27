package dev.muon.medievalorigins.attribute;

import dev.muon.medievalorigins.MedievalOrigins;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {
    public static final Holder<Attribute> RANGED_DAMAGE = register("summon.ranged_damage",
            new RangedAttribute("attribute.medievalorigins.summon.ranged_damage", 0.0D, 0.0D, 2048.0D)
                    .setSyncable(true));

    private static Holder<Attribute> register(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE,
                MedievalOrigins.loc(name),
                attribute);
    }

    public static void register() {
        // Registration happens in static initializer
    }
}