package com.clefal.mmofx.common.effect;

import com.robertx22.age_of_exile.aoe_data.database.spells.schools.BasicAttackSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;

import java.util.ArrayList;
import java.util.List;

public class FXCache {
    public static void init(){
        List<String> list =List.of(
                WaterSpells.CHILLING_FIELD,
                WaterSpells.FROST_NOVA_AOE,
                WaterSpells.HEART_OF_ICE,
                WaterSpells.FROZEN_ORB,

                BasicAttackSpells.FIREBALL_ID,
                BasicAttackSpells.FROSTBALL_ID,

                //NatureSpells.REFRESH

                FireSpells.FIRE_NOVA_ID


        );
        FX_CACHE.addAll(list);

    }

    public static List<String> FX_CACHE = new ArrayList<>();
}
