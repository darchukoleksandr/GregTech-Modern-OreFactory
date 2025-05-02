package com.gregtechceu.gtof.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

public class GTOFRecipeTypes {

    public static final GTRecipeType ORE_FACTORY_1_RECIPES = GTRecipeTypes.register("ore_factory_1", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(9, 12, 3, 3).setEUIO(IO.IN)
            .prepareBuilder(recipeBuilder -> recipeBuilder.EUt(GTValues.VA[GTValues.LuV]))
            .setSlotOverlay(false, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR);

    public static final GTRecipeType ORE_FACTORY_2_RECIPES = GTRecipeTypes.register("ore_factory_2", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(9, 12, 3, 3).setEUIO(IO.IN)
            .prepareBuilder(recipeBuilder -> recipeBuilder.EUt(GTValues.VA[GTValues.LuV]))
            .setSlotOverlay(false, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR);

    public static final GTRecipeType ORE_FACTORY_3_RECIPES = GTRecipeTypes.register("ore_factory_3", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(9, 12, 3, 3).setEUIO(IO.IN)
            .prepareBuilder(recipeBuilder -> recipeBuilder.EUt(GTValues.VA[GTValues.LuV]))
            .setSlotOverlay(false, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR);
           
    public static final GTRecipeType ORE_FACTORY_4_RECIPES = GTRecipeTypes.register("ore_factory_4", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(9, 12, 3, 3).setEUIO(IO.IN)
            .prepareBuilder(recipeBuilder -> recipeBuilder.EUt(GTValues.VA[GTValues.LuV]))
            .setSlotOverlay(false, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR); 
           
    public static final GTRecipeType ORE_FACTORY_5_RECIPES = GTRecipeTypes.register("ore_factory_5", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(9, 12, 3, 3).setEUIO(IO.IN)
            .prepareBuilder(recipeBuilder -> recipeBuilder.EUt(GTValues.VA[GTValues.LuV]))
            .setSlotOverlay(false, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR); 

    public static void init() {
        com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] GTECRecipeTypes init");
    }
}
