package com.clefal.mmofx.common.packets;

import com.clefal.mmofx.common.effect.PositionEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXInfoHolder.clientPlayerEntityFXHolder;
import static com.clefal.mmofx.common.data.FXUtilities.getResFromRawString;

public class sendRestorationPositionPacket extends MyPacket<sendRestorationPositionPacket> {

    UUID entityUUID = null;

    String resourceType;

    Vector3f pos = new Vector3f(0,0,0);

    public sendRestorationPositionPacket(UUID entityUUID, String resourceType, Vector3f pos) {
        this.entityUUID = entityUUID;
        this.resourceType = resourceType;
        this.pos = pos;
    }

    public sendRestorationPositionPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellfxupdate");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();
        this.resourceType = buf.readUtf();
        this.pos = buf.readVector3f();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.resourceType);
        buf.writeVector3f(this.pos);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Optional.ofNullable(clientPlayerEntityFXHolder.computeIfAbsent(this.entityUUID, x -> {
            FX FXResource = FXHelper.getFX(getResFromRawString(resourceType));
            return FXResource == null ? null : new PositionEffect(this.entityUUID, FXResource, pos).startAndReturn(true);
        })).ifPresent(positionEffect -> {
            positionEffect.setNewPos(this.pos);
            positionEffect.setLifespan(0);
        });
    }

    @Override
    public MyPacket<sendRestorationPositionPacket> newInstance() {
        return new sendRestorationPositionPacket();
    }
}
