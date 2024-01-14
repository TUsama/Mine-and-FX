package com.clefal.mmofx.common.spellmodify.spellmodifiers;

import com.clefal.mmofx.common.spellaction.FXSpellAction;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class FireNovaModify implements ISpellModifier{

    @Override
    public List<ComponentPart> modifyOnCast() {
        var list = new ArrayList<ComponentPart>();
        list.add(PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(80d, "fire_nova")
                .put(MapField.HEIGHT, 0.5D)));
        return list;
    }
}
