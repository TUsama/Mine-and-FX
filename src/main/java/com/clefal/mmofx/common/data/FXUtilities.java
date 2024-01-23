package com.clefal.mmofx.common.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class FXUtilities {


    public static ResourceLocation getResFromRawString(String txt) {
        return new ResourceLocation("mmofx:" + txt);
    }

    public static List<ServerPlayer> getPlayerWithinRange(Vec3 origin, Level world, Double range){
        if (world instanceof ServerLevel) {
            var wholePlayerList = ((ServerLevel)world).players();
            return wholePlayerList.stream()
                    .map(player -> Pair.of(player, player.blockPosition()))
                    .filter(blockPosPair -> blockPosPair.getRight().closerToCenterThan(origin, range))
                    .map(Pair::getLeft)
                    .toList();
        }
        return new ArrayList<>();
    }

}
