package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class IcarusWingsPowerType extends PowerType {
    private final ItemStack wingsType;

    public IcarusWingsPowerType(ItemStack wingsType, Optional<EntityCondition> condition) {
        super(condition);
        this.wingsType = wingsType;
    }

    public ItemStack getWingsType() {
        return wingsType;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.ICARUS_WINGS;
    }

    public static final PowerConfiguration<IcarusWingsPowerType> FACTORY = PowerConfiguration.conditionedOf(
            MedievalOrigins.loc("icarus_wings"),
            new SerializableData()
                    .add("wings_type", SerializableDataTypes.ITEM_STACK),
            (data, condition) -> new IcarusWingsPowerType(
                    data.get("wings_type"),
                    condition
            ),
            (type, data) -> data.instance()
                    .set("wings_type", type.getWingsType())
    );
}