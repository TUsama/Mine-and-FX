package com.clefal.mmofx.common.spellmodify.spellmodifiers;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;

public interface ISpellModifier {
    Spell modify(Spell spell);
}
