package com.clefal.mmofx.common.spellmodify;

import com.clefal.mmofx.common.spellaction.FXSpellAction;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class SpellModifier {

    @Singular(value = "onCast", ignoreNullCollections = true)
    public List<ComponentPart> onCast;

    public String identifier;

    public static SpellModifier fireNova = SpellModifier.builder()
            .identifier(FireSpells.FIRE_NOVA_ID)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(30d, "fire_nova")
                    .put(MapField.HEIGHT, 0.5D)))
            .build();

    public static SpellModifier frostNova = SpellModifier.builder()
            .identifier(WaterSpells.FROST_NOVA_AOE)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(30d, "fire_nova")
                    .put(MapField.HEIGHT, 0.5D)))
            .build();

    public static SpellModifier heartOfIce = SpellModifier.builder()
            .identifier(WaterSpells.HEART_OF_ICE)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(20D * 2, "heart_of_ice")
                    .put(MapField.HEIGHT, 0.5D)))
            .build();




}
