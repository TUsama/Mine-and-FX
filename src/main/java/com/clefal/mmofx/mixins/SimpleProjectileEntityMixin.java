package com.clefal.mmofx.mixins;

import com.clefal.mmofx.common.spellmodify.DisableOption;
import com.clefal.mmofx.common.spellmodify.SpellModifiers;
import com.clefal.mmofx.entity.IFXSender;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(SimpleProjectileEntity.class)
public abstract class SimpleProjectileEntityMixin implements IFXSender {
    @Shadow public abstract CalculatedSpellData getSpellData();

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
    @Inject(method = "getItem", at = @At(value = "HEAD"), cancellable = true)
    private void mine_and_fx$modify_getItem(CallbackInfoReturnable<ItemStack> cir){
        SimpleProjectileEntity en = (SimpleProjectileEntity)(Object)this;
        Stream.of(Optional.ofNullable(new SpellModifiers().getModifier(this.getSpellData().getSpell().identifier))
                .map(spellModifier -> spellModifier.disableOption)
                .orElseGet(() -> new DisableOption().setDisableItemRender(true)))
                .filter(disableOption -> disableOption.disableItemRender)
                .forEach(x -> cir.setReturnValue(new ItemStack(Items.AIR)));

    }
}
