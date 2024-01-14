package com.clefal.mmofx.common.spellmodify;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;

import java.util.Arrays;

public class ComponentPartInjector {


    public static Spell injectOnCast(Spell spell, ComponentPart... parts){
        Arrays.stream(parts)
                .filter(componentPart -> !spell.attached.on_cast.contains(componentPart))
                .forEach(componentPart -> {
                    spell.attached.on_cast.add(componentPart);
                    componentPart.addActivationRequirement(EntityActivation.ON_CAST);
                });
        return spell;
    }
}
