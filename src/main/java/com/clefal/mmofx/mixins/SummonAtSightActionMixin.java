package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.SpellEntityInitPacket;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonAtSightAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Optional;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;


@Mixin(value = SummonAtSightAction.class)
public class SummonAtSightActionMixin {

    @Inject(
            method = "tryActivate",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/robertx22/age_of_exile/database/data/spells/spell_classes/SpellUtils;initSpellEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/LivingEntity;Lcom/robertx22/age_of_exile/database/data/spells/entities/CalculatedSpellData;Lcom/robertx22/age_of_exile/database/data/spells/components/MapHolder;)V",
                    shift = At.Shift.AFTER
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void getEntityUUIDAndPos(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data, CallbackInfo ci, Optional projectile, Double distance, Double height, HitResult ray, Vec3 pos, Entity en, @Share("entity") LocalRef<Entity> entity, @Share("pos") LocalRef<Vec3> finalPosition, @Share("ctx") LocalRef<SpellCtx> sctx) {
        entity.set(en);
        finalPosition.set(pos);
        sctx.set(ctx);
    }

    @Inject(
            method = "tryActivate",
            at = @At(value = "RETURN"),
            remap = false
    )
    public void sendFXNotification(CallbackInfo ci, @Share("entity") LocalRef<Entity> entity, @Share("pos") LocalRef<Vec3> finalPosition, @Share("ctx") LocalRef<SpellCtx> sctx) {
        getPlayerWithinRange(finalPosition.get(), sctx.get().world, 128.0D)
                .stream()
                .filter(FXInfoHolder::readFXConfigValue)
                .toList()
                .forEach(serverPlayer -> {
                    Packets.sendToClient(serverPlayer, new SpellEntityInitPacket(entity.get().getUUID(), sctx.get().calculatedSpellData.getSpell().identifier));
                });

    }


}
