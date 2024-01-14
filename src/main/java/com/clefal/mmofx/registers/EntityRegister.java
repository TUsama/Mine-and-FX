package com.clefal.mmofx.registers;

import com.clefal.mmofx.entity.FXEntity;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegister {
    public static RegObj<EntityType<FXEntity>> FX_ENTITY = FXEntity(FXEntity::new, "fx_entity");

    public EntityRegister() {
    }

    public static void init() {
    }

    private static <T extends Entity> RegObj<EntityType<T>> FXEntity(EntityType.EntityFactory<T> factory,
                                                                     String id) {

        RegObj<EntityType<T>> def = Def.entity(id, () -> EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(0.5F, 0.5F)
                .build(id));

        return def;
    }

}
