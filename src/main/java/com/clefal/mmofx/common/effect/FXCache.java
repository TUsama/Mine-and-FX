package com.clefal.mmofx.common.effect;

import java.util.ArrayList;
import java.util.List;

public class FXCache {
    public static void init(){
        FX_CACHE.add("chilling_field");
        FX_CACHE.add("fire_nova");
        FX_CACHE.add("fireball");
        FX_CACHE.add("frozen_orb");
        FX_CACHE.add("heart_of_ice");
        FX_CACHE.add("ice_shard");
        FX_CACHE.add("refresh");
        
    }

    public static List<String> FX_CACHE = new ArrayList<>();
}
