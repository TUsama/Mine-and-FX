package com.clefal.mmofx.common.effect;

import com.lowdragmc.photon.client.emitter.IParticleEmitter;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXEffect;
import com.lowdragmc.photon.client.fx.FXHelper;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.util.UUID;
@Setter
public class PositionEffect extends FXEffect {


    UUID entityUUID = null;

    FX skillFX;

    Vector3f pos = new Vector3f(0,0,0);
    Vector3f newPos = this.pos;

    Boolean stop;
    private Integer lifespan;

    public PositionEffect(UUID entityUUID, FX fx, Vector3f pos) {
        super(fx, Minecraft.getInstance().level);
        this.entityUUID = entityUUID;
        this.skillFX = fx;
        this.pos = pos;
        this.stop = false;
        this.lifespan = 0;
    }

    @Override
    public void start() {
        this.emitters.clear();
        this.emitters.addAll(fx.generateEmitters());
        if (this.emitters.isEmpty()) return;
        /*if (!allowMulti) {
            var effects = CACHE.computeIfAbsent(pos, p -> new ArrayList<>());
            var iter = effects.iterator();
            while (iter.hasNext()) {
                var effect = iter.next();
                boolean removed = false;
                if (effect.getEmitters().stream().noneMatch(e -> e.self().isAlive())) {
                    iter.remove();
                    removed = true;
                }
                if (effect.fx.equals(fx) && !removed) {
                    return;
                }
            }
            effects.add(this);
        }*/
        for (var emitter : emitters) {
            if (!emitter.isSubEmitter()) {
                emitter.reset();
                emitter.self().setDelay(delay);
                emitter.emmitToLevel(this, level, pos.x, pos.y, pos.z, xRotation, yRotation, zRotation);

            }
        }
    }

    @Override
    public boolean updateEmitter(IParticleEmitter emitter) {
        var finalNewPos = new Vector3f((float) (newPos.x + xOffset), (float) (newPos.y + yOffset), (float) (newPos.z + zOffset));
        this.lifespan++;
        if(!stop && this.lifespan <= 20 * 6){
            emitter.updatePos(finalNewPos);
            return false;
        }
        emitter.remove(forcedDeath);
        return forcedDeath;
    }

}
