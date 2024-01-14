package com.clefal.mmofx.common.spellmodify;

import com.clefal.mmofx.common.spellmodify.spellmodifiers.FireNovaModify;
import com.clefal.mmofx.common.spellmodify.spellmodifiers.FrostNovaModify;
import com.clefal.mmofx.common.spellmodify.spellmodifiers.ISpellModifier;

import javax.annotation.Nullable;
import java.util.HashMap;

public class SpellModifierFactory {

    protected static HashMap<String, ISpellModifier> modifiers = new HashMap<>();

    @Nullable
    public static ISpellModifier getModifier(String identifier){
        return modifiers.get(identifier);
    }

    public static void init(){
        modifiers.put("fire_nova", new FireNovaModify());
        modifiers.put("frost_nova", new FrostNovaModify());
    }
}
