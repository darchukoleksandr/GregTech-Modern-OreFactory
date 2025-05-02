package com.gregtechceu.gtof.common.data;

import com.gregtechceu.gtof.data.recipe.MiscRecipes;
import com.gregtechceu.gtof.data.recipe.OreRecipeHandler;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class GTOFRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        OreRecipeHandler.init(provider);
        MiscRecipes.init(provider);
    }
}
