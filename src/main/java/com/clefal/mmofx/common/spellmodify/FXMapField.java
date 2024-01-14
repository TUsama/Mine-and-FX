package com.clefal.mmofx.common.spellmodify;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

public class FXMapField<T> extends MapField<T> {

    private final String id;

    //public static HashMap<String, FXMapField> MAP = new HashMap<>();

    public static MapField<String> FX_ENTITY = make("fx_en");

    public static MapField<String> SKILL_FX = make("skill_fx");

    public static MapField<Boolean> HIDE_IN_FX = make("hide_in_fx");

    public static MapField<Boolean> IS_ENTITY_EFFECT = make("is_entity_effect");

    public FXMapField(String id) {
        super(id);
        this.id = id;
    }

    private static <T> MapField<T> make(String f) {
        FXMapField<T> field = new FXMapField<T>(f);
        MAP.put(field.GUID(), field);
        return field;
    }

    @Override
    public String GUID() {
        return this.id;
    }

    public static void init() {

    }
}
