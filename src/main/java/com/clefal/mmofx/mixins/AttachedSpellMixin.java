package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.spellmodify.SpellModifiers;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AttachedSpell.class)
public class AttachedSpellMixin {
    @Inject(method = "onCast", at = @At(value = "HEAD"), remap = false)
    private void injectFXEntity(SpellCtx ctx, CallbackInfo ci){
        Optional.ofNullable(new SpellModifiers().getModifier(ctx.calculatedSpellData.getSpell().identifier))
                .ifPresent(spellModifier -> spellModifier.onCast
                                .forEach(x -> x.tryActivate(ctx)));
    }
}
