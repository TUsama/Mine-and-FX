package com.clefal.mmofx;

import com.clefal.mmofx.common.effect.FXCache;
import com.clefal.mmofx.common.packets.C2SPacketRegister;
import com.clefal.mmofx.common.packets.S2CPacketRegister;
import com.clefal.mmofx.common.packets.askForFXConfigPacket;
import com.clefal.mmofx.common.spellmodify.FXMapField;
import com.clefal.mmofx.common.spellmodify.SpellModifierFactory;
import com.clefal.mmofx.registers.EntityRegister;
import com.clefal.mmofx.registers.FXRenderRegister;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.Packets;
import lombok.val;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModMain.MODID)
public class ModMain {

  public static final String MODID = "mmofx";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMain() {
    EntityRegister.init();
    S2CPacketRegister.register();
    C2SPacketRegister.register();
    FXMapField.init();
    SpellModifierFactory.init();
    FXCache.init();

    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
      FMLJavaModLoadingContext.get().getModEventBus().addListener(FXRenderRegister::regRenders);
      ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.clientSpec);
    });


    ExileEvents.ON_PLAYER_LOGIN.register(new EventConsumer<ExileEvents.OnPlayerLogin>() {
      @Override
      public void accept(ExileEvents.OnPlayerLogin event) {
        val player = event.player;
        Level level = player.level();

        if (!level.isClientSide() && ModList.get().getMods().stream()
                .map(IModInfo::getModId)
                .anyMatch(s -> s.contentEquals("photon"))) {
          Packets.sendToClient(player, new askForFXConfigPacket());
        }
      }
    });
  }

  private void setup(final FMLCommonSetupEvent event) {
  }

  private void setupClient(final FMLClientSetupEvent event) {
  }
}
