package com.clefal.mmofx;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigManager {

  public static final ForgeConfigSpec clientSpec;
  public static final ConfigManager CLIENT;

  static {
    final Pair<ConfigManager, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigManager::new);
    clientSpec = specPair.getRight();
    CLIENT = specPair.getLeft();
  }

  ConfigManager(ForgeConfigSpec.Builder b) {
    b.comment("Client Configs")
            .push("general");

    ENABLE_PHOTON_FX = b.define("enable_photon_fx", true);

    b.pop();
  }

  public ForgeConfigSpec.BooleanValue ENABLE_PHOTON_FX;



  public static ConfigManager getConfig() {
    return CLIENT;
  }
}
