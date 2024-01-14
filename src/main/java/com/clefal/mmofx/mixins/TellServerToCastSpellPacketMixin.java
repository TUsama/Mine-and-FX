package com.clefal.mmofx.mixins;

import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TellServerToCastSpellPacket.class)
public class TellServerToCastSpellPacketMixin {
    /*@ModifyVariable(method = "onReceived", at = @At(value = "INVOKE",
            target = "Lcom/robertx22/age_of_exile/vanilla_mc/packets/spells/TellServerToCastSpellPacket;tryCastSpell(Lnet/minecraft/world/entity/player/Player;Lcom/robertx22/age_of_exile/database/data/spells/components/Spell;)Z"),
            remap = false,
            name = "spell"
    )
    private Spell modifySpell(Spell value){
        ISpellModifier modifier = getModifier(value.identifier);
        return modifier != null ? modifier.modify(value) : value;
    }*/
}
