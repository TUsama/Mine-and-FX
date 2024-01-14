package com.clefal.mmofx.registers;

import com.robertx22.age_of_exile.database.data.spells.entities.renders.MySpriteRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class FXRenderRegister {

    public static void regRenders(EntityRenderersEvent.RegisterRenderers x) {


        x.registerEntityRenderer(EntityRegister.FX_ENTITY.get(), (d) -> new MySpriteRenderer<>(d, Minecraft.getInstance()
                .getItemRenderer()));



    }

}
