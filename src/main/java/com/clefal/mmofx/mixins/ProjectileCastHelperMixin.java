package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.SpellEntityInitPacket;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;


@Mixin(value = ProjectileCastHelper.class)
public class ProjectileCastHelperMixin {

    @Shadow(remap = false)
    Vec3 pos;
    @Shadow(remap = false)
    CalculatedSpellData data;

    @Shadow(remap = false)
    LivingEntity caster;

    @Inject(
            method = "cast",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/robertx22/age_of_exile/database/data/spells/spell_classes/SpellUtils;initSpellEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/LivingEntity;Lcom/robertx22/age_of_exile/database/data/spells/entities/CalculatedSpellData;Lcom/robertx22/age_of_exile/database/data/spells/components/MapHolder;)V",
                    shift = At.Shift.AFTER
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void getEntityUUIDAndPos(CallbackInfo ci, Level world, float addYaw, Vec3 posAdd, int i, Vec3 vec31, Quaternionf quaternionf, Vec3 vec3, Vector3f finalVel, AbstractArrow en, @Share("entity") LocalRef<AbstractArrow> entity, @Share("pos") LocalRef<Vec3> finalPosition){
        entity.set(en);
        finalPosition.set(pos.add(posAdd));
    }

    @Inject(
            method = "cast",
            at = @At(value = "RETURN"),
            remap = false
    )
    public void sendFXNotification(CallbackInfo ci, @Share("entity") LocalRef<AbstractArrow> entity, @Share("pos") LocalRef<Vec3> finalPosition) {
        if (entity == null || finalPosition == null) {
            return;
        }
        getPlayerWithinRange(finalPosition.get(), caster.level(), 128.0D)
                .stream()
                .filter(FXInfoHolder::readFXConfigValue)
                .toList()
                .forEach(serverPlayer -> {
                    Packets.sendToClient(serverPlayer, new SpellEntityInitPacket(entity.get().getUUID(), data.getSpell().identifier));
                });

    }


}
