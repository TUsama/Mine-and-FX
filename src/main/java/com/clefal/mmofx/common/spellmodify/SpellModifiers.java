package com.clefal.mmofx.common.spellmodify;

import com.clefal.mmofx.common.spellaction.FXSpellAction;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.BasicAttackSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HolySpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class SpellModifiers {
    public HashMap<String, Supplier<SpellModifier>> modifiers = new HashMap<>();
    public SpellModifier fireNova = SpellModifier.builder()
            .identifier(FireSpells.FIRE_NOVA_ID)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(30d, FireSpells.FIRE_NOVA_ID + "_FX_entity").put(MapField.HEIGHT, 0.5D)))
            .build();
    public SpellModifier frostNova = SpellModifier.builder()
            .identifier(WaterSpells.FROST_NOVA_AOE)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(30d, WaterSpells.FROST_NOVA_AOE + "_FX_entity")
                    .put(MapField.HEIGHT, 0.5D)))
            .build();
    public SpellModifier heartOfIce = SpellModifier.builder()
            .identifier(WaterSpells.HEART_OF_ICE)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(20D * 2, WaterSpells.HEART_OF_ICE + "_FX_entity")
                    .put(MapField.HEIGHT, 0.5D)))
            .build();
    public SpellModifier gongStrike = SpellModifier.builder()
            .identifier(HolySpells.GONG_STRIKE_ID)
            .onCast(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(30d, HolySpells.GONG_STRIKE_ID + "_FX_entity")
                    .put(MapField.HEIGHT, 0.5D)))
            .build();
    public SpellModifier poisonBall = SpellModifier.builder()
            .identifier(BasicAttackSpells.POISONBALL_ID)
            .disableOption(new DisableOption().setDisableItemRender(true))
            .build();

    public static Optional<Supplier<SpellModifier>> getModifier(String identifier) {
        return Optional.ofNullable(new SpellModifiers().loadIn().get(identifier));
    }

    private HashMap<String, Supplier<SpellModifier>> loadIn() {
        fireNova.load(modifiers);
        frostNova.load(modifiers);
        heartOfIce.load(modifiers);
        gongStrike.load(modifiers);
        poisonBall.load(modifiers);
        return modifiers;
    }
}

