package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.effect.ConcurrentFXHelper;
import com.clefal.mmofx.common.packets.SpellParticlePacket;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import com.robertx22.library_of_exile.utils.geometry.ShapeHelper;
import com.robertx22.library_of_exile.utils.geometry.XTimesData;
import lombok.val;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;
import static com.clefal.mmofx.common.data.FXUtilities.getResFromRawString;


@Mixin(value = ParticleInRadiusAction.class)
public class ParticleInRadiusActionMixin{

    @Inject(method = "lambda$tryActivate$0",
            at = @At(value = "INVOKE",
                    target = "Lcom/robertx22/library_of_exile/utils/geometry/ShapeHelper;spawnParticle(Lnet/minecraft/world/level/Level;Lorg/joml/Vector3d;Lnet/minecraft/core/particles/ParticleOptions;Lorg/joml/Vector3d;)V"),
            remap = false,
            cancellable = true
    )
    private static void injectActivation(float yrand, ParticleShape shape, Vec3 pos, float finalRadius, Vec3 vel, float height, ParticleMotion finalMotion1, SpellCtx ctx, float motionMulti, ShapeHelper c, SimpleParticleType particle, XTimesData x, CallbackInfo ci){
        val original = new MyPosition(pos);
        val nearPlayer = getPlayerWithinRange(pos, ctx.world, 128.0D);
            nearPlayer.stream()
                    .collect(Collectors.groupingBy(FXInfoHolder::readFXConfigValue))
                    .forEach((key, value) -> {
                        if(!key){
                            value.forEach(serverPlayer -> Packets.sendToClient(serverPlayer, new SpellParticlePacket(original, particle, new MyPosition(vel))));
                        }
                        if(Optional.ofNullable(ConcurrentFXHelper.getFX(getResFromRawString(ctx.calculatedSpellData.getSpell().identifier))).isEmpty()){
                            value.forEach(serverPlayer -> Packets.sendToClient(serverPlayer, new SpellParticlePacket(original, particle, new MyPosition(vel))));
                        }
                    });
        ci.cancel();
    }
}