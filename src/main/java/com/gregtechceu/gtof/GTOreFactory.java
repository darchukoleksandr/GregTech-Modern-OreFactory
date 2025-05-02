package com.gregtechceu.gtof;

import com.gregtechceu.gtof.common.data.GTOFRecipeTypes;
import com.gregtechceu.gtof.common.data.GTOFMachines;
import com.gregtechceu.gtof.data.GTOFDatagen;
import com.gregtechceu.gtof.registry.GTOFRegistries;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtof.config.ConfigHolder;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GTOreFactory.MOD_ID)
public class GTOreFactory {

    public static final String MOD_ID = "gtof";
    public static final Logger LOGGER = LogManager.getLogger();

    public GTOreFactory() {
        ConfigHolder.init(); // Forcefully init GT config because fabric doesn't allow dependents to load after dependencies

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("[GregtechOreFactory] GTOreFactory config loaded");

        modEventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        modEventBus.addGenericListener(MachineDefinition.class, this::registerMachines);

        MinecraftForge.EVENT_BUS.register(this);

        // var bus = FMLJavaModLoadingContext.get().getModEventBus();
        // bus.register(this);

        GTOFDatagen.init();

        GTOFRegistries.REGISTRATE.registerRegistrate();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        LOGGER.info("[GregtechOreFactory] GTOreFactory registerRecipeTypes");
        GTOFRecipeTypes.init();
    }

    public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        LOGGER.info("[GregtechOreFactory] GTOreFactory registerMachines");
        GTOFMachines.init();
    }
}
