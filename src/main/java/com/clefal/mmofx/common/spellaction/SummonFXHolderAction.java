package com.clefal.mmofx.common.spellaction;

import com.clefal.mmofx.common.data.FXInfoHolder;
import com.clefal.mmofx.common.packets.SpellEntityInitPacket;
import com.clefal.mmofx.common.spellmodify.FXMapField;
import com.clefal.mmofx.entity.FXEntity;
import com.clefal.mmofx.registers.EntityRegister;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static com.clefal.mmofx.common.data.FXUtilities.getPlayerWithinRange;

public class SummonFXHolderAction extends SpellAction {
    public SummonFXHolderAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, FXMapField.FX_ENTITY, MapField.LIFESPAN_TICKS));
    }


    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClientSide) {

            Optional<EntityType<?>> entity = EntityType.byString(data.get(FXMapField.FX_ENTITY));


            Vec3 pos = ctx.caster.position();
            Double addY = data.getOrDefault(MapField.HEIGHT, 0D);

            Vec3 finalPos = new Vec3(pos.x, pos.y + addY, pos.z);

            Level world = ctx.caster.level();


            if (data.getOrDefault(FXMapField.IS_ENTITY_EFFECT, false)) {
                FXEntity en = new FXEntity(world, true);
                SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
                en.setPos(finalPos);
                ctx.world.addFreshEntity(en);
                getPlayerWithinRange(finalPos, world, 128.0D)
                        .stream()
                        .filter(FXInfoHolder::readFXConfigValue)
                        .toList()
                        .forEach(serverPlayer ->
                                Packets.sendToClient(serverPlayer, new SpellEntityInitPacket(en.getUUID(), ctx.calculatedSpellData.getSpell().identifier)));


            } else {

                FXEntity en = new FXEntity(world, false);
                SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
                en.setPos(finalPos);
                ctx.world.addFreshEntity(en);
                getPlayerWithinRange(finalPos, world, 128.0D)
                        .stream()
                        .filter(FXInfoHolder::readFXConfigValue)
                        .toList()
                        .forEach(serverPlayer ->
                                Packets.sendToClient(serverPlayer, new SpellEntityInitPacket(en.getUUID(), ctx.calculatedSpellData.getSpell().identifier)));

            }
        }
    }


    public MapHolder createFXHolder(Double lifespan) {
        MapHolder c = createBase(lifespan, false);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(FXMapField.FX_ENTITY, EntityType.getKey(EntityRegister.FX_ENTITY.get()).toString());
        return c;
    }

    public MapHolder createFXHolder(Double lifespan, String entityName) {
        MapHolder c = createBase(lifespan, false);
        c.put(MapField.ENTITY_NAME, entityName);
        c.put(FXMapField.FX_ENTITY, EntityType.getKey(EntityRegister.FX_ENTITY.get()).toString());
        return c;
    }

    private MapHolder createBase(Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.GRAVITY, gravity);
        c.type = GUID();
        return c;
    }


    @Override
    public String GUID() {
        return "fx_holder";
    }

}
