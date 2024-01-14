package com.clefal.mmofx.common.spellmodify.spellmodifiers;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;

import java.util.List;

public interface ISpellModifier {
    List<ComponentPart> modifyOnCast();
}
