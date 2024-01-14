package com.clefal.mmofx.common.spellmodify;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class SpellInjector {


    public static Spell injectOnCast(Spell spell, ComponentPart... parts){
        Arrays.stream(parts)
                .filter(componentPart -> !spell.attached.on_cast.contains(componentPart))
                .peek(componentPart -> Minecraft.getInstance().player.displayClientMessage(Component.literal("remain one ComponentPart"), false))
                .forEach(componentPart -> {
                    spell.attached.on_cast.add(componentPart);
                    componentPart.addActivationRequirement(EntityActivation.ON_CAST);
                });
        return spell;
    }
}
