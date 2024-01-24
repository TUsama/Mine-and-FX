package com.clefal.mmofx.mixins;

import com.clefal.mmofx.mixininterfaces.IClientLevelMixin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.entity.EntityTickList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ClientLevel.class)
public class ClientLevelMixin implements IClientLevelMixin {

    @Final
    @Shadow
    EntityTickList tickingEntities;
    @Unique
    public EntityTickList mine_and_FX$getTickingEntities(){
        return tickingEntities;
    }
}
