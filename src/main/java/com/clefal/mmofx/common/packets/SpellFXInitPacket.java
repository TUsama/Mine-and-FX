package com.clefal.mmofx.common.packets;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXUtilities.getResFromRawString;

public class SpellFXInitPacket extends MyPacket<SpellFXInitPacket> {

    UUID entityUUID = null;

    String skillIdentifier = "";

    Boolean allowMulti;


    public SpellFXInitPacket(UUID entityUUID, String skillFXName) {
        this.entityUUID = entityUUID;
        this.skillIdentifier = skillFXName;
        this.allowMulti = true;
    }

    public SpellFXInitPacket(UUID entityUUID, String skillFXName, Boolean allowMulti) {
        this.entityUUID = entityUUID;
        this.skillIdentifier = skillFXName;
        this.allowMulti = allowMulti;
    }


    public SpellFXInitPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellfxinit");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();
        this.skillIdentifier = buf.readUtf();
        this.allowMulti = buf.readBoolean();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.skillIdentifier);
        buf.writeBoolean(this.allowMulti);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Optional<FX> FXResource = Optional.ofNullable(FXHelper.getFX(getResFromRawString(skillIdentifier)));
        if (FXResource.isPresent()) {
            Iterable<Entity> entities = ((ClientLevel) ctx.getPlayer().level()).entitiesForRendering();
            entities.forEach(x -> {
                if (x.getUUID().equals(entityUUID)) {
                    EntityEffect entityEffect = new EntityEffect(FXResource.get(), ctx.getPlayer().level(), x);
                    entityEffect.setAllowMulti(allowMulti);
                    entityEffect.start();
                }
            });
        }
    }

    @Override
    public MyPacket<SpellFXInitPacket> newInstance() {
        return new SpellFXInitPacket();
    }
}
