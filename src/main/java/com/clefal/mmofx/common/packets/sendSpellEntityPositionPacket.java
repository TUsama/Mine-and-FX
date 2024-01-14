package com.clefal.mmofx.common.packets;

import com.clefal.mmofx.common.effect.PositionEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXInfoHolder.clientPlayerEntityFXHolder;
import static com.clefal.mmofx.common.data.FXUtilities.getSkillFXFromRawString;

public class sendSpellEntityPositionPacket extends MyPacket<sendSpellEntityPositionPacket> {

    UUID entityUUID = null;

    String skillIdentifier;

    Vector3f pos = new Vector3f(0,0,0);

    public sendSpellEntityPositionPacket(UUID entityUUID, String skillIdentifier, Vector3f pos) {
        this.entityUUID = entityUUID;
        this.skillIdentifier = skillIdentifier;
        this.pos = pos;
    }

    public sendSpellEntityPositionPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellfxupdate");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();
        this.skillIdentifier = buf.readUtf();
        this.pos = buf.readVector3f();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.skillIdentifier);
        buf.writeVector3f(this.pos);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if(!ctx.getPlayer().level().isClientSide()){
            return;
        }

        if (clientPlayerEntityFXHolder.containsKey(this.entityUUID)){
            Minecraft.getInstance().player.displayClientMessage(Component.literal("update one"), false);
            val positionEffect = clientPlayerEntityFXHolder.get(this.entityUUID);
            positionEffect.setNewPos(this.pos);
            positionEffect.setLifespan(0);
        } else {
            Optional<FX> FXResource = Optional.ofNullable(FXHelper.getFX(getSkillFXFromRawString(skillIdentifier)));
            if(FXResource.isPresent()){
                Minecraft.getInstance().player.displayClientMessage(Component.literal("create one"), false);
                val effect = new PositionEffect(this.entityUUID, FXResource.get(), pos);
                clientPlayerEntityFXHolder.put(this.entityUUID, effect);
                effect.start();
            }
        }
    }

    @Override
    public MyPacket<sendSpellEntityPositionPacket> newInstance() {
        return new sendSpellEntityPositionPacket();
    }
}
