package com.clefal.mmofx.common.packets;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXUtilities.getResFromRawString;

public class SpellRestorationFXBatchInitPacket extends MyPacket<SpellRestorationFXBatchInitPacket> {

    List<UUID> entityUUID;

    ResourceType resourceType;

    Boolean allowMulti;


    public SpellRestorationFXBatchInitPacket(List<UUID> entityUUID, ResourceType skillFXName) {
        this.entityUUID = entityUUID;
        this.resourceType = skillFXName;
        this.allowMulti = true;
    }

    public SpellRestorationFXBatchInitPacket(List<UUID> entityUUID, ResourceType skillFXName, Boolean allowMulti) {
        this.entityUUID = entityUUID;
        this.resourceType = skillFXName;
        this.allowMulti = allowMulti;
    }


    public SpellRestorationFXBatchInitPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellfxinit");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readList(FriendlyByteBuf::readUUID);
        this.resourceType = buf.readEnum(ResourceType.class);
        this.allowMulti = buf.readBoolean();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeCollection(entityUUID, FriendlyByteBuf::writeUUID);
        buf.writeEnum(this.resourceType);
        buf.writeBoolean(this.allowMulti);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Optional<FX> FXResource = Optional.ofNullable(FXHelper.getFX(getResFromRawString(resourceType.id)));
        if (FXResource.isPresent()) {
            Iterable<Entity> entities = ((ClientLevel) ctx.getPlayer().level()).entitiesForRendering();
            HashSet<UUID> targetsUUID = new HashSet<>(entityUUID);
            entities.forEach(x -> {
                if (targetsUUID.contains(x.getUUID())) {
                    EntityEffect entityEffect = new EntityEffect(FXResource.get(), ctx.getPlayer().level(), x);
                    entityEffect.setAllowMulti(allowMulti);
                    entityEffect.start();
                }
            });
        }
    }

    @Override
    public MyPacket<SpellRestorationFXBatchInitPacket> newInstance() {
        return new SpellRestorationFXBatchInitPacket();
    }
}
