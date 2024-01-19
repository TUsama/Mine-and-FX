package com.clefal.mmofx.common.spellmodify;

import javax.annotation.Nullable;
import java.util.HashMap;

import static com.clefal.mmofx.common.spellmodify.SpellModifier.*;

public class SpellModifiers {
    public static HashMap<String, SpellModifier> modifiers = new HashMap<>();

    @Nullable
    public SpellModifier getModifier(String identifier){
        return modifiers.get(identifier);
    }


    public static void init(){
        modifiers.put(fireNova.identifier, fireNova);
        modifiers.put(frostNova.identifier, frostNova);
        modifiers.put(heartOfIce.identifier, heartOfIce);
    }





}

