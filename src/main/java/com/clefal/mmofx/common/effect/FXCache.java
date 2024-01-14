package com.clefal.mmofx.common.effect;

import java.util.ArrayList;
import java.util.List;

public class FXCache {
    public static void init(){
        List<String> list =List.of("chilling_field", "fire_nova", "fireball", "frozen_orb", "heart_of_ice", "ice_shard", "refresh", "frost_nova");
        FX_CACHE.addAll(list);

    }

    public static List<String> FX_CACHE = new ArrayList<>();
}
