package com.clefal.mmofx.mixins;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

import static com.clefal.mmofx.common.effect.FXCache.FX_CACHE;

@Mixin(ParticleInRadiusAction.class)
public class ParticleInRadiusActionMixin {
    @Inject(method = "tryActivate", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void injectActivation(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data, CallbackInfo ci){
        if(ctx.world.isClientSide){
            return;
        }
        var identifier = ctx.calculatedSpellData.getSpell().identifier;
        if(FX_CACHE.contains(identifier)){
            ci.cancel();
        }
    }
}
