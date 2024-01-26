package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.SpellFXInitPacket;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonBlockAction;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;


@Mixin(value = SummonBlockAction.class)
public class SummonBlockActionMixin {

    @Inject(
            method = "tryActivate",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/robertx22/age_of_exile/database/data/spells/spell_classes/SpellUtils;initSpellEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/LivingEntity;Lcom/robertx22/age_of_exile/database/data/spells/entities/CalculatedSpellData;Lcom/robertx22/age_of_exile/database/data/spells/components/MapHolder;)V",
                    shift = At.Shift.AFTER
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void getEntityUUIDAndPos(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data, CallbackInfo ci, MyPosition pos, float yoff, float xoff, float zoff, boolean found, Block block, StationaryFallingBlockEntity be, @Share("entity") LocalRef<StationaryFallingBlockEntity> entity, @Share("pos") LocalRef<Vec3> finalPosition, @Share("ctx") LocalRef<SpellCtx> sctx, @Share("checkif") LocalBooleanRef ifFound) {
        entity.set(be);
        finalPosition.set(pos);
        sctx.set(ctx);
        ifFound.set(found);
    }

    @Inject(
            method = "tryActivate",
            at = @At(value = "RETURN"),
            remap = false
    )
    public void sendFXNotification(CallbackInfo ci, @Share("entity") LocalRef<StationaryFallingBlockEntity> entity, @Share("pos") LocalRef<Vec3> finalPosition, @Share("ctx") LocalRef<SpellCtx> sctx, @Share("checkif") LocalBooleanRef ifFound) {
        if (entity == null || finalPosition == null || sctx == null || ifFound == null) {
            return;
        }
        getPlayerWithinRange(finalPosition.get(), sctx.get().world, 128.0D)
                .stream()
                .filter(FXInfoHolder::readFXConfigValue)
                .toList()
                .forEach(serverPlayer -> {
                    Packets.sendToClient(serverPlayer, new SpellFXInitPacket(entity.get().getUUID(), sctx.get().calculatedSpellData.getSpell().identifier));
                });

    }


}
