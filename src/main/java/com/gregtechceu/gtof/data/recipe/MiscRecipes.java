package com.gregtechceu.gtof.data.recipe;

import com.gregtechceu.gtof.GTOreFactory;
import com.gregtechceu.gtof.common.data.GTOFMachines;
import com.gregtechceu.gtof.common.data.GTOFRecipeTypes;
import com.gregtechceu.gtof.common.machine.multiblock.electric.OreFactoryMachine;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.gregtechceu.gtceu.common.data.GTMachines;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IntersectionIngredient;
import net.minecraftforge.fluids.FluidStack;

import com.mojang.datafixers.util.Pair;

import java.util.function.Consumer;
import java.util.Arrays;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.HIGH_SIFTER_OUTPUT;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.gregtechceu.gtceu.common.data.machines.GTResearchMachines.*;

public class MiscRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        // VanillaRecipeHelper.addShapedRecipe(provider, GTOreFactory.id("ore_factory"), GTOFMachines.ORE_FACTORY.asStack(),
        //     "SCS", "S S", "S S",
        //     'S', GCYRItems.SPACE_FABRIC.asStack(), 'C', CustomTags.EV_CIRCUITS
        // );
        // MachineDefinition oreWasher = Arrays.stream(GTMachines.ORE_WASHER).filter(washer -> washer.getTier() == GTValues.IV).findAny().orElse(null);
        MachineDefinition hullDefinition = Arrays.stream(GTMachines.HULL).filter(hull -> hull.getTier() == GTValues.LuV).findAny().orElse(null);
        
        ASSEMBLY_LINE_RECIPES.recipeBuilder("ore_factory")
            .inputItems(hullDefinition.asStack())
            .inputItems(ELECTRIC_MOTOR_LuV.asStack(), 32)
            .inputItems(ELECTRIC_PISTON_LuV.asStack(), 8)
            .inputItems(ELECTRIC_PUMP_LuV.asStack(), 16)
            .inputItems(CONVEYOR_MODULE_LuV.asStack(), 8)
            .inputItems(ROBOT_ARM_LuV.asStack(), 8)
            .inputItems(CustomTags.UHV_CIRCUITS, 8)
            .inputItems(wireGtQuadruple, NaquadahAlloy,32)
            .inputItems(COMPONENT_GRINDER_TUNGSTEN,4)
            .inputItems(plate, NaquadahAlloy,32)
            .inputItems(rotor, NaquadahAlloy,16)
            .inputFluids(SolderingAlloy.getFluid(L * 2))
            .inputFluids(Lubricant.getFluid(500))
            .outputItems(GTOFMachines.ORE_FACTORY)
            // .scannerResearch(b -> b
            //     .researchStack(oreWasher.asStack())
            //     .duration(2400)
            //     .EUt(VA[IV])
            // )
            .duration(60*20).EUt(90000).save(provider);
    }
}
