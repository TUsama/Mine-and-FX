package com.clefal.mmofx.mixins;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.RestoreHealthAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;

@Mixin(value = RestoreHealthAction.class)
public class RestoreHealthActionMixin {

    @Inject(
            method = "tryActivate",
            at = @At(value = "INVOKE",
                    target = "Lcom/robertx22/age_of_exile/uncommon/effectdatas/RestoreResourceEvent;Activate()V"),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD

    )
    private void injectRestoreFX(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data, CallbackInfo ci, ValueCalculation calc, int value, Iterator var6, LivingEntity t, RestoreResourceEvent restore) {

    }

}
