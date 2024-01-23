package com.clefal.mmofx.common.spellmodify;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import lombok.Builder;
import lombok.Singular;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

@Builder
public class SpellModifier {

    @Singular(value = "onCast", ignoreNullCollections = true)
    public List<ComponentPart> onCast;

    public String identifier;


    public void load(HashMap<String, Supplier<SpellModifier>> map){
        map.put(identifier, () -> this);
    }


}
