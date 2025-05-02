package com.gregtechceu.gtof.common.data;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import org.jetbrains.annotations.NotNull;

public class GTOFRecipeModifiers {

    public static final RecipeModifier ORE_FACTORY_PARALLEL = GTOFRecipeModifiers::oreFactoryParallel;

    public static @NotNull ModifierFunction oreFactoryParallel(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        int defaultParallels = 1024;
        if (machine instanceof IMultiController controller && controller.isFormed()) {
            int parallels = ParallelLogic.getParallelAmount(machine, recipe, defaultParallels);

            if (parallels == 1) return ModifierFunction.IDENTITY;
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(parallels))
                    .eutMultiplier(parallels)
                    .parallels(parallels)
                    .build();
        }
        return ModifierFunction.IDENTITY;
    }
}
