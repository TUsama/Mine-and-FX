package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.SpellEntityInitPacket;
import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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


    @Inject(
            method = "cast",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
                    shift = At.Shift.AFTER
            ),
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void sendFXNotification(CallbackInfo ci, Level world, float addYaw, Vec3 posAdd, int i, Vec3 vec31, Quaternionf quaternionf, Vec3 vec3, Vector3f finalVel, AbstractArrow en, BlockPos pos, EntityFinder.Setup finder, LivingEntity target, Vec3 vel) {
        getPlayerWithinRange(this.pos.add(posAdd), world, 128.0D)
                .stream()
                .filter(FXInfoHolder::readFXConfigValue)
                .toList()
                .forEach(serverPlayer -> {
                    Packets.sendToClient(serverPlayer, new SpellEntityInitPacket(en.getUUID(), data.getSpell().identifier));
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("send a pack!"), false);
                });

    }
}
