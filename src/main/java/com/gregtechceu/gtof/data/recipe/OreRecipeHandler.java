package com.gregtechceu.gtof.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.gregtechceu.gtof.common.data.GTOFRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IntersectionIngredient;
import net.minecraftforge.fluids.FluidStack;

import com.mojang.datafixers.util.Pair;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.HIGH_SIFTER_OUTPUT;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class OreRecipeHandler {

    public static void init(Consumer<FinishedRecipe> provider) {
        for (TagPrefix ore : TagPrefix.ORES.keySet()) {
            if (ConfigHolder.INSTANCE.worldgen.allUniqueStoneTypes || TagPrefix.ORES.get(ore).shouldDropAsItem()) {
                ore.executeHandler(provider, PropertyKey.ORE, OreRecipeHandler::processOre);
            }
        }
        // rawOre.executeHandler(provider, PropertyKey.ORE, OreRecipeHandler::processOre);

        // rawOre.executeHandler(provider, PropertyKey.ORE, OreRecipeHandler::processRawOre);
    }

    public static void processOre(TagPrefix orePrefix, Material material, OreProperty property, Consumer<FinishedRecipe> provider) {
        Material byproductMaterial = GTUtil.selectItemInList(0, material, property.getOreByProducts(), Material.class);
        ItemStack dustByproductStack = ChemicalHelper.get(dust, byproductMaterial, property.getByProductMultiplier());
        ItemStack oreByproductStack = ChemicalHelper.get(gem, byproductMaterial);
        if (oreByproductStack.isEmpty())
            oreByproductStack = ChemicalHelper.get(dust, byproductMaterial);
        ItemStack crushedStack = ChemicalHelper.get(crushed, material);
        int oreTypeMultiplier = TagPrefix.ORES.get(orePrefix).isDoubleDrops() ? 2 : 1;
        int oreMultiplier = property.getOreMultiplier();
        crushedStack.setCount(crushedStack.getCount() * oreMultiplier);

        if (crushedStack.isEmpty()) return;

        // log("material " + material.getName() + "; crushedStack " + crushedStack.getCount() + "; oreTypeMultiplier " + oreTypeMultiplier + "; oreMultiplier " + oreMultiplier);

        ItemStack dustStack = ChemicalHelper.get(TagPrefix.dust, material);
        
        String prefixString = orePrefix == ore ? "" : orePrefix.name + "_";

        // route 1
        Material refinedToDustByproductMaterial = GTUtil.selectItemInList(2, material, property.getOreByProducts(), Material.class);
        ItemStack refinedToDustByproductStack = ChemicalHelper.get(dust, refinedToDustByproductMaterial, 1);

        // route 2
        Material purifiedToDustByproductMaterial = GTUtil.selectItemInList(1, material, property.getOreByProducts(), Material.class);
        ItemStack purifiedToDustByproductStack = ChemicalHelper.get(dust, purifiedToDustByproductMaterial);

        GTRecipeBuilder builder1 = GTOFRecipeTypes.ORE_FACTORY_1_RECIPES
            .recipeBuilder("ore_factory_" + prefixString + material.getName() + "_macerate_wash_thermal_macerate")
            .inputItems(IntersectionIngredient.of(
                Ingredient.of(orePrefix.getItemTags(material)[0]),
                Ingredient.of(orePrefix.getItemParentTags()[0])
            ))
            .inputFluids(Water.getFluid(2000))
            .inputFluids(Lubricant.getFluid(2))
            .outputItems(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustStack))
            .outputItems(TagPrefix.dust, GTMaterials.Stone) // crushed -> purified
            .chancedOutput(oreByproductStack, 1400, 850) // ore -> crushed (1 gem or dust)
            .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * 2 * oreTypeMultiplier, dustByproductStack), "1/3", 0) // washer crushed -> purified + thermal purified -> refined
            // .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustByproductStack), "1/3", 0) // washer crushed -> purified
            // .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustByproductStack), "1/3", 0) // thermal purified -> refined
            .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, refinedToDustByproductStack), 1400, 850) // macerator refined -> dust
            .EUt(30)
            .duration(30 * 20);
        for (MaterialStack secondaryMaterial : orePrefix.secondaryMaterials()) { // ore -> crushed (rock dust byproduct)
            if (secondaryMaterial.material().hasProperty(PropertyKey.DUST)) {
                ItemStack dustGemStack = ChemicalHelper.getGem(secondaryMaterial);
                builder1.chancedOutput(dustGemStack, 6700, 800);
            }
        }
        builder1.save(provider);

        
        GTRecipeBuilder builder2 = GTOFRecipeTypes.ORE_FACTORY_2_RECIPES
            .recipeBuilder("ore_factory_" + prefixString + material.getName() + "_macerate_wash_macerate_centrifuge")
            .inputItems(IntersectionIngredient.of(
                Ingredient.of(orePrefix.getItemTags(material)[0]),
                Ingredient.of(orePrefix.getItemParentTags()[0])
            ))
            .inputFluids(Water.getFluid(2000))
            .inputFluids(Lubricant.getFluid(2))
            .outputItems(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustStack))
            .outputItems(TagPrefix.dust, GTMaterials.Stone) // crushed -> purified
            .chancedOutput(oreByproductStack, 1400, 850) // ore -> crushed (1 gem or dust)
            .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustByproductStack), "1/3", 0) // washer crushed -> purified
            .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, purifiedToDustByproductStack), 1400, 850) // macerator purified ore -> purified dust
            .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, purifiedToDustByproductStack), "1/9", 0) // centrifuge purified dust -> dust
            .EUt(30)
            .duration(15 * 20);
        for (MaterialStack secondaryMaterial : orePrefix.secondaryMaterials()) { // ore -> crushed (rock dust byproduct)
            if (secondaryMaterial.material().hasProperty(PropertyKey.DUST)) {
                ItemStack dustGemStack = ChemicalHelper.getGem(secondaryMaterial);
                builder2.chancedOutput(dustGemStack, 6700, 800);
            }
        }
        builder2.save(provider);


        GTRecipeBuilder builder3 = GTOFRecipeTypes.ORE_FACTORY_3_RECIPES
            .recipeBuilder("ore_factory_" + prefixString + material.getName() + "_macerate_macerate_centrifuge")
            .inputItems(IntersectionIngredient.of(
                Ingredient.of(orePrefix.getItemTags(material)[0]),
                Ingredient.of(orePrefix.getItemParentTags()[0])
            ))
            .inputFluids(Lubricant.getFluid(2))
            .outputItems(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustStack))
            .chancedOutput(oreByproductStack, 1400, 850) // macerator ore -> crushed ore (1 gem or dust)
            .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, dustByproductStack), 1400, 850) // macerator crushed ore -> impure dust
            .EUt(30)
            .duration(10 * 20);
        for (MaterialStack secondaryMaterial : orePrefix.secondaryMaterials()) { // ore -> crushed (1 rock dust byproduct)
            if (secondaryMaterial.material().hasProperty(PropertyKey.DUST)) {
                ItemStack dustGemStack = ChemicalHelper.getGem(secondaryMaterial);
                builder3.chancedOutput(dustGemStack, 6700, 800);
            }
        }
        if (byproductMaterial.hasProperty(PropertyKey.DUST)) { // centrifuge impure dust -> centrifuge
            builder3.chancedOutput(TagPrefix.dust, byproductMaterial, oreMultiplier * 2, "1/9", 0);
        } else {
            builder3.outputFluids(byproductMaterial.getFluid(L / 9 * oreMultiplier));
        }
        builder3.save(provider);


        
        if (material.hasProperty(PropertyKey.GEM)) {
            ItemStack exquisiteStack = ChemicalHelper.get(gemExquisite, material);
            ItemStack flawlessStack = ChemicalHelper.get(gemFlawless, material);
            ItemStack gemStack = ChemicalHelper.get(gem, material);
            ItemStack flawedStack = ChemicalHelper.get(gemFlawed, material);
            ItemStack chippedStack = ChemicalHelper.get(gemChipped, material);

            GTRecipeBuilder builder4 = GTOFRecipeTypes.ORE_FACTORY_4_RECIPES
                .recipeBuilder("ore_factory_" + prefixString + material.getName() + "_macerate_wash_sift")
                .inputItems(IntersectionIngredient.of(
                    Ingredient.of(orePrefix.getItemTags(material)[0]),
                    Ingredient.of(orePrefix.getItemParentTags()[0])
                ))
                .inputFluids(Water.getFluid(2000))
                .inputFluids(Lubricant.getFluid(2))
                .outputItems(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustStack))
                .outputItems(TagPrefix.dust, GTMaterials.Stone) // crushed -> purified
                .chancedOutput(oreByproductStack, 1400, 850) // ore -> crushed (1 gem or dust)
                .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustByproductStack), "1/3", 0) // washer crushed -> purified
                .EUt(30)
                .duration(20 * 20);

            if (material.hasFlag(HIGH_SIFTER_OUTPUT)) {
                builder4.chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, exquisiteStack), 500, 150)
                        .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, flawlessStack), 1500, 200)
                        .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, gemStack), 5000, 1000)
                        .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, dustStack), 2500, 500);

                if (!flawedStack.isEmpty())
                    builder4.chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, flawedStack), 2000, 500);
                if (!chippedStack.isEmpty())
                    builder4.chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, chippedStack), 3000, 350);
            } else {
                builder4.chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, exquisiteStack), 300, 100)
                        .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, flawlessStack), 1000, 150)
                        .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, gemStack), 3500, 500)
                        .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, dustStack), 5000, 750);

                if (!flawedStack.isEmpty())
                    builder4.chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, flawedStack), 2500, 300);
                if (!chippedStack.isEmpty())
                    builder4.chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, chippedStack), 3500, 400);
            }
            
            for (MaterialStack secondaryMaterial : orePrefix.secondaryMaterials()) { // ore -> crushed (1 rock dust byproduct)
                if (secondaryMaterial.material().hasProperty(PropertyKey.DUST)) {
                    ItemStack dustGemStack = ChemicalHelper.getGem(secondaryMaterial);
                    builder4.chancedOutput(dustGemStack, 6700, 800);
                }
            }
            builder4.save(provider);
        }


        
        if (property.getWashedIn().getFirst() != null) {
            Material washingByproduct = GTUtil.selectItemInList(3, material, property.getOreByProducts(), Material.class);
            Pair<Material, Integer> washedInTuple = property.getWashedIn();
            FluidStack bathFluidStack = washedInTuple.getFirst().getFluid(washedInTuple.getSecond());
            bathFluidStack.setAmount(bathFluidStack.getAmount() * 2);
            ItemStack washStack = ChemicalHelper.get(dust, washingByproduct, property.getByProductMultiplier());

            GTRecipeBuilder builder5 = GTOFRecipeTypes.ORE_FACTORY_5_RECIPES
                .recipeBuilder("ore_factory_" + prefixString + material.getName() + "_macerate_bath_macerate_centrifuge")
                .inputItems(IntersectionIngredient.of(
                    Ingredient.of(orePrefix.getItemTags(material)[0]),
                    Ingredient.of(orePrefix.getItemParentTags()[0])
                ))
                .inputFluids(bathFluidStack)
                .inputFluids(Lubricant.getFluid(2))
                .outputItems(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, dustStack))
                .chancedOutput(oreByproductStack, 1400, 850) // ore -> crushed (1 gem or dust)
                .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, washStack), 7000, 580) // bath crushed -> purified
                .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2, ChemicalHelper.get(dust, Stone)), 4000, 650) // crushed -> purified
                .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, purifiedToDustByproductStack), 1400, 850) // macerator purified ore -> purified dust
                .chancedOutput(GTUtil.copyAmount(oreMultiplier * 2 * oreTypeMultiplier, purifiedToDustByproductStack), "1/9", 0) // centrifuge purified dust -> dust
                .EUt(30)
                .duration(17 * 20);
            for (MaterialStack secondaryMaterial : orePrefix.secondaryMaterials()) { // ore -> crushed (rock dust byproduct)
                if (secondaryMaterial.material().hasProperty(PropertyKey.DUST)) {
                    ItemStack dustGemStack = ChemicalHelper.getGem(secondaryMaterial);
                    builder5.chancedOutput(dustGemStack, 6700, 800);
                }
            }
            builder5.save(provider);
        }
    }

    public static void log(String message) {
        com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] OreRecipeHandler: " + message);
    }
}
