package com.clefal.mmofx.common.spellmodify.spellmodifiers;

import com.clefal.mmofx.common.spellaction.FXSpellAction;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.ArrayList;
import java.util.List;

public class HeartOfIceModify implements ISpellModifier{

    @Override
    public List<ComponentPart> modifyOnCast() {
        var list = new ArrayList<ComponentPart>();
        list.add(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(20D * 5, "heart_of_ice")
                .put(MapField.HEIGHT, 0.5D)
        ));
        return list;
    }
}