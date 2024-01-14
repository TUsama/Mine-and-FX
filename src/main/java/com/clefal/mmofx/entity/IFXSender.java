package com.clefal.mmofx.entity;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.sendSpellEntityDeath;
import com.clefal.mmofx.common.packets.sendSpellEntityPositionPacket;
import com.robertx22.age_of_exile.database.data.spells.entities.IDatapackSpellEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;

public interface IFXSender {


    default void sendTickFXPackets(List<ServerPlayer> list, IDatapackSpellEntity entity, Double d) {

        if(entity instanceof StationaryFallingBlockEntity entity1){
            if(entity1.getSpellData() == null){
                return;
            }
            listChecker(list, entity1, d);
            

            list.stream()
                    .filter(Objects::nonNull)
                    .forEach(serverPlayer ->
                            Packets.sendToClient(serverPlayer, new sendSpellEntityPositionPacket(entity1.getUUID(), entity1.getSpellData().getSpell().identifier, entity1.position().toVector3f())));
            return;
        }
        if(entity instanceof SimpleProjectileEntity entity1){
            if(entity1.getSpellData() == null){
                return;
            }
            listChecker(list, entity1, d);

            list.stream()
                    .filter(Objects::nonNull)
                    .forEach(serverPlayer ->
                            Packets.sendToClient(serverPlayer, new sendSpellEntityPositionPacket(entity1.getUUID(), entity1.getSpellData().getSpell().identifier, entity1.position().toVector3f())));
            
            return;
        }

        if(entity instanceof FXEntity entity1){
            if(entity1.getSpellData() == null){
                return;
            }
            listChecker(list, entity1, d);
            list.stream()
                    .filter(Objects::nonNull)
                    .forEach(serverPlayer ->
                            Packets.sendToClient(serverPlayer, new sendSpellEntityPositionPacket(entity1.getUUID(), entity1.getSpellData().getSpell().identifier, entity1.position().toVector3f())));
        }
    }

    private void listChecker(List<ServerPlayer> list, Entity entity, Double d){
        if(list.isEmpty()){
            
            list.addAll(getPlayerWithinRange(entity.position(), entity.level(), d)
                    .stream()
                    .filter(FXInfoHolder::readFXConfigValue)
                    .toList()
            );
        }

    }

    default void sendEndFXPackets(List<ServerPlayer> list, Entity entity) {
        if(!list.isEmpty()){
            list.forEach(serverPlayer ->
                    Packets.sendToClient(serverPlayer, new sendSpellEntityDeath(entity.getUUID())));
        }


    }
}
