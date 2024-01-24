package com.clefal.mmofx.common.packets;

import com.clefal.mmofx.mixininterfaces.IClientLevelMixin;
import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTickList;

import java.util.Optional;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXUtilities.getResFromRawString;

public class SpellEntityInitPacket extends MyPacket<SpellEntityInitPacket> {

    UUID entityUUID = null;

    String skillIdentifier = "";


    public SpellEntityInitPacket(UUID entityUUID, String skillFXName) {
        this.entityUUID = entityUUID;
        this.skillIdentifier = skillFXName;
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
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.skillIdentifier);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Optional<FX> FXResource = Optional.ofNullable(FXHelper.getFX(getResFromRawString(skillIdentifier)));
        Minecraft.getInstance().player.displayClientMessage(Component.literal("current world is:" + ctx.getPlayer().level() + ", and get a name:" + skillIdentifier + ", exist:" + FXResource.isPresent()), false);
        if (FXResource.isPresent()) {
            Iterable<Entity> entities = ((ClientLevel) ctx.getPlayer().level()).entitiesForRendering();
            Minecraft.getInstance().player.displayClientMessage(Component.literal("gained all entity!"), false);
            entities.forEach(x -> {
                if (x.getUUID().equals(entityUUID)) {
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("a Entity matched!"), false);
                    new EntityEffect(FXResource.get(), ctx.getPlayer().level(), x).start();
                } else {
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("found one but don't matched! the UUID got is " + entityUUID + ", but found one is " + x.getUUID()), false);

                }
            });
        }
    }

    @Override
    public MyPacket<SpellEntityInitPacket> newInstance() {
        return new SpellEntityInitPacket();
    }
}
