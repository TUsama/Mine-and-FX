package com.clefal.mmofx.mixins;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.clefal.mmofx.common.spellmodify.SpellModifiers.getModifier;

@Mixin(AttachedSpell.class)
public class AttachedSpellMixin {
    @Shadow(remap = false)
    public List<ComponentPart> on_cast;

    @Inject(method = "onCast",
            at = @At(value = "HEAD"),
            remap = false
    )
    private void injectFXEntity(SpellCtx ctx, CallbackInfo ci) {
        HashSet<String> holderType = new HashSet<>();
        on_cast.forEach(x -> x.acts.forEach(y -> holderType.add(y.type)));
        Predicate<String> checkIfOtherSummonActionExist = x -> x.equals("projectile") || x.equals("summon_block") || x.equals("summon_at_sight");
        getModifier(ctx.calculatedSpellData.getSpell().identifier)
                .map(Supplier::get)
                .filter(spellModifier -> !(Boolean.TRUE.equals(spellModifier.onlyWorkWhenNonOtherSummonAction) && holderType.stream().anyMatch(checkIfOtherSummonActionExist)))
                .map(spellModifier -> spellModifier.onCast)
                .ifPresent(componentParts -> componentParts.forEach(y -> y.tryActivate(ctx)));
    }
}
