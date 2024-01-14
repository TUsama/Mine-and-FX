package com.clefal.mmofx.common.spellaction;

import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.List;

public abstract class FXSpellAction extends SpellAction {
    public FXSpellAction(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public static SummonFXHolderAction FX_HOLDER = of(new SummonFXHolderAction());

    private static <T extends SpellAction> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }
}
