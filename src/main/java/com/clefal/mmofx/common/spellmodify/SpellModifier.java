package com.clefal.mmofx.common.spellmodify;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import lombok.Builder;
import lombok.Singular;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

@Builder
public class SpellModifier {

    @Singular(value = "onCast", ignoreNullCollections = true)
    @Nullable
    public List<ComponentPart> onCast;
    @Builder.Default
    public DisableOption disableOption = new DisableOption();
    public String identifier;

    public void load(HashMap<String, Supplier<SpellModifier>> map){
        if (identifier != null) {
            map.put(identifier, () -> this);
        } else {
            throw new NullPointerException("load a SpellModifier with null identifier!");
        }
    }


}
