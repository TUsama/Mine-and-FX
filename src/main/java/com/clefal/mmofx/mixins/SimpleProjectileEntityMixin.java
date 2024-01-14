package com.clefal.mmofx.mixins;

import com.clefal.mmofx.entity.IFXSender;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SimpleProjectileEntity.class)
public class SimpleProjectileEntityMixin implements IFXSender {
@Unique
List<ServerPlayer> mine_and_FX$playerList = new ArrayList<>();
@Inject(method = "tick", at = @At(value = "HEAD"))
    public void injectTick(CallbackInfo ci){
    SimpleProjectileEntity en = (SimpleProjectileEntity)(Object)this;
    sendTickFXPackets(mine_and_FX$playerList, en, 128.0D);
}

    @Inject(method = "remove", at = @At(value = "HEAD"))
    public void injectDeath(CallbackInfo ci){
        SimpleProjectileEntity en = (SimpleProjectileEntity)(Object)this;
        sendEndFXPackets(mine_and_FX$playerList, en);
    }
}
