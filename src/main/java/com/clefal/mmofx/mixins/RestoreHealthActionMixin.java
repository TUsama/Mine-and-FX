package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.SpellRestorationFXBatchInitPacket;
import com.google.common.collect.ImmutableList;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.RestoreHealthAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;

@Mixin(value = RestoreHealthAction.class)
public class RestoreHealthActionMixin {

    @Inject(
            method = "tryActivate",
            at = @At(value = "INVOKE",
                    target = "Lcom/robertx22/age_of_exile/uncommon/effectdatas/RestoreResourceEvent;Activate()V",
                    shift = At.Shift.AFTER
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILSOFT

    )
    private void injectRestoreFX(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data, CallbackInfo ci, ValueCalculation calc, int value, Iterator var6, LivingEntity t, RestoreResourceEvent restore) {
        ImmutableList.Builder<UUID> builder = ImmutableList.builder();
        targets.forEach(x -> builder.add(x.getUUID()));
        getPlayerWithinRange(ctx.caster.position(), ctx.world, 64.0D)
                .stream()
                .filter(FXInfoHolder::readFXConfigValue)
                .toList()
                .forEach(serverPlayer -> {
                    Packets.sendToClient(serverPlayer, new SpellRestorationFXBatchInitPacket(builder.build(), ResourceType.health));
                });

    }

}
