package com.clefal.mmofx.common.packets;

import com.clefal.mmofx.common.effect.PositionEffect;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXInfoHolder.clientPlayerEntityFXHolder;

public class SpellEntityInitPacket {
/*
    UUID entityUUID = null;

    String skillIdentifier = "";

    Vec3 pos = new Vec3(0,0,0);

    public SpellEntityInitPacket(UUID entityUUID, Vec3 pos, String skillFXName) {
        this.entityUUID = entityUUID;
        this.skillIdentifier = skillFXName;
        this.pos = pos;
    }


    public SpellEntityInitPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellfxinit");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();
        this.skillIdentifier = buf.readUtf();
        var x = buf.readDouble();
        var y = buf.readDouble();
        var z = buf.readDouble();
        this.pos = new Vec3(x, y, z);
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.skillIdentifier);
        buf.writeDouble(this.pos.x);
        buf.writeDouble(this.pos.y);
        buf.writeDouble(this.pos.z);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if(!ctx.getPlayer().level().isClientSide()){
            return;
        }
        Optional<ResourceLocation> FXResource = Optional.ofNullable(ResourceLocation.tryParse("mmofx:" + skillIdentifier));
        if(FXResource.isPresent()){
            var effect = new PositionEffect(this.entityUUID, FXResource.get(), pos.toVector3f());
            clientPlayerEntityFXHolder.put(this.entityUUID, effect);
            effect.start();
        }


    }

    @Override
    public MyPacket<SpellEntityInitPacket> newInstance() {
        return new SpellEntityInitPacket();
    }*/
}
