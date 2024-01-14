package com.clefal.mmofx.common.spellmodify.spellmodifiers;

import com.clefal.mmofx.common.spellaction.FXSpellAction;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.clefal.mmofx.common.spellmodify.SpellInjector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class FireNovaModify implements ISpellModifier{

    @Override
    public Spell modify(Spell spell) {
        val fireNova = PartBuilder.justAction(FXSpellAction.FX_HOLDER.createFXHolder(80d, "fire_nova")
                .put(MapField.HEIGHT, 0.5D));
        Minecraft.getInstance().player.displayClientMessage(Component.literal("it actually come here!"), false);
        return SpellInjector.injectOnCast(spell, fireNova);
    }
}
