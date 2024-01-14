package com.clefal.mmofx.common.packets;

import com.clefal.mmofx.ConfigManager;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.clefal.mmofx.common.data.FXInfoHolder.writeFXConfigValueFromPacket;

public class FXConfigCheckerPacket extends MyPacket<FXConfigCheckerPacket> {

    public Boolean ifFXEnable;

    public FXConfigCheckerPacket(){
    }


    @Override
    public ResourceLocation getIdentifier()  {
        return new ResourceLocation(SlashRef.MODID, "fxconfigchecker");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.ifFXEnable = buf.readBoolean();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeBoolean(ConfigManager.getConfig().ENABLE_PHOTON_FX.get());
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Minecraft.getInstance().player.displayClientMessage(Component.literal("get feedback!"), false);
        writeFXConfigValueFromPacket(ctx.getPlayer(), this.ifFXEnable);
        Minecraft.getInstance().player.displayClientMessage(Component.literal("player FX status: " + ifFXEnable), false);
    }

    @Override
    public MyPacket<FXConfigCheckerPacket> newInstance() {
        return new FXConfigCheckerPacket();
    }
}
