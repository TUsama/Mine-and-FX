package com.clefal.mmofx.common.effect;

import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentFXHelper extends FXHelper {

    private final static ConcurrentHashMap<ResourceLocation, FX> CACHE = new ConcurrentHashMap<>();
    @Nullable
    public static FX getFX(ResourceLocation fxLocation) {
        return CACHE.computeIfAbsent(fxLocation, location -> {
            ResourceLocation resourceLocation = new ResourceLocation(fxLocation.getNamespace(), FX_PATH + fxLocation.getPath() + ".fx");
            try (var inputStream = Minecraft.getInstance().getResourceManager().open(resourceLocation);) {
                var tag = NbtIo.readCompressed(inputStream);
                return new FX(fxLocation, getEmitters(tag), tag);
            } catch (Exception ignored) {
                return null;
            }
        });
    }

}
