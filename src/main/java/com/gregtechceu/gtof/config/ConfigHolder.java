package com.gregtechceu.gtof.config;

import com.gregtechceu.gtof.GTOreFactory;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = GTOreFactory.MOD_ID)
public class ConfigHolder {

    public static ConfigHolder INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(ConfigHolder.class, ConfigFormats.yaml()).getConfigInstance();
            }
        }
    }

    @Configurable
    public MachineConfigs machines = new MachineConfigs();

    public static class MachineConfigs {

        @Configurable
        @Configurable.Comment("Integrate Ore Factory Options")
        public OreFactory oreFactory = new OreFactory();

        public static class OreFactory {

            @Configurable
            @Configurable.Comment({ "Strict mode. Enforces GTNH style utility block (muffler, energy hatch etc.) placement for successful multiblock forming.", "Default: true" })
            public boolean strictMode = true;

            @Configurable
            @Configurable.Comment({ "Max recipe parallel.", "Default: 1024" })
            public int parallels = 1024;
        }
    }
}
