package com.clefal.mmofx.common.data;

import com.clefal.mmofx.ConfigManager;
import com.clefal.mmofx.common.effect.PositionEffect;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FXInfoHolder {

    public static Map<UUID, PositionEffect> clientPlayerEntityFXHolder = new HashMap<>();
    private static Map<UUID, Boolean> playerFXEnableMap = new HashMap<>();

    public static void writeFXConfigValue(Player player){
        UUID UUID = player.getUUID();
        Boolean ifFXEnable = ConfigManager.getConfig().ENABLE_PHOTON_FX.get();
        playerFXEnableMap.put(UUID, ifFXEnable);
    }

    public static void writeFXConfigValueFromPacket(Player player, Boolean b){
        UUID UUID = player.getUUID();
        playerFXEnableMap.put(UUID, b);
    }

    public static Boolean readFXConfigValue(Player player){
        UUID UUID = player.getUUID();
        return playerFXEnableMap.getOrDefault(UUID, false);
    }

    public static void init(){}

}
