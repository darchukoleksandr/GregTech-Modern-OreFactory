        
package com.gregtechceu.gtof.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GCYMBlocks;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import com.gregtechceu.gtof.common.machine.multiblock.electric.OreFactoryMachine;
import com.gregtechceu.gtof.config.ConfigHolder;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;

import static com.gregtechceu.gtceu.common.data.GTMaterialBlocks.MATERIAL_BLOCKS;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded", "unused"})
public class GTOFMachines {

    public static void init() {
        com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] GTECMachines init");
        com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] strictMode: " + ConfigHolder.INSTANCE.machines.oreFactory.strictMode);
        com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] paralells: " + ConfigHolder.INSTANCE.machines.oreFactory.parallels);
    }

    public final static MultiblockMachineDefinition ORE_FACTORY = GTRegistration.REGISTRATE
        // .multiblock("ore_factory", WorkableElectricMultiblockMachine::new)
        .multiblock("ore_factory", OreFactoryMachine::new)
        .langValue("Ore Factory")
        .tooltips(Component.translatable("gtceu.multiblock.ore_factory_descr_0"))
        .tooltips(Component.translatable("gtceu.multiblock.ore_factory_descr_1"))
        .tooltips(Component.translatable("gtceu.multiblock.ore_factory_descr_2", ConfigHolder.INSTANCE.machines.oreFactory.parallels))
        .tooltips(Component.translatable("gtceu.multiblock.ore_factory_descr_3"))
        .tooltips(Component.translatable("gtceu.multiblock.ore_factory_descr_4"))
        // .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
        //     Component.translatable("gtceu.macerator")))
        .rotationState(RotationState.ALL)
        .recipeTypes(
            GTOFRecipeTypes.ORE_FACTORY_1_RECIPES,
            GTOFRecipeTypes.ORE_FACTORY_2_RECIPES,
            GTOFRecipeTypes.ORE_FACTORY_3_RECIPES,
            GTOFRecipeTypes.ORE_FACTORY_4_RECIPES,
            GTOFRecipeTypes.ORE_FACTORY_5_RECIPES
        )
        .recipeModifiers(
            GTOFRecipeModifiers.ORE_FACTORY_PARALLEL,
            // GTRecipeModifiers.OC_PERFECT_SUBTICK
            GTRecipeModifiers.OC_NON_PERFECT_SUBTICK
            // LuV 1024 = 30 sec
            // LuV 1 = 18 t
            // GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK)
        )
        .appearanceBlock(GTBlocks.CASING_ALUMINIUM_FROSTPROOF)
        // .pattern(definition -> FactoryBlockPattern.start() // dev test
        //     .aisle("XSX", "XXX")
        //     .where('S', Predicates.controller(Predicates.blocks(definition.get())))
        //     .where('X', Predicates.blocks(GCYMBlocks.CASING_SECURE_MACERATION.get()).setMinGlobalLimited(1)
        //         .or(Predicates.autoAbilities(definition.getRecipeTypes()))
        //         .or(Predicates.autoAbilities(false, false, true)))
        //     .build()
        // )
        .pattern(definition -> {
            if (ConfigHolder.INSTANCE.machines.oreFactory.strictMode)
            return FactoryBlockPattern.start()
                .aisle("CCCCCC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CCCCCC     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("CCCCCCCCCCC", "CF  F CXXXC", "CF  F CXXXC", "CF  F CXXXC", "CF  F CXXXC", "CIIIICCXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "           ")
                .aisle("CCCCCCCCCCC", "C BB  X   O", "C BB  XPPPO", "C BB  X   X", "C BB  XPPPX", "CIIIICX   X", "      XPPPX", "      X   X", "      XPPPX", "      X   X", "      XPPPX", "       WMW ")
                .aisle("CCCCCCCCCCC", "C BB  X   O", "C BB  XPPPO", "C BB  X   X", "C BB  XPPPX", "CIIIICX   X", "      XPPPX", "      X   X", "      XPPPX", "      X   X", "      XPPPX", "       WMW ")
                .aisle("CCCCCCCCCCC", "CF  F CXNXC", "CF  F CXTXC", "CF  F CXXXC", "CF  F CXXXC", "CIIIICCXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "           ")
                .aisle("CCCCCC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CCCCCC     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .where(' ', Predicates.any())
                .where('T', Predicates.controller(Predicates.blocks(definition.get())))
                .where('N', Predicates.abilities(PartAbility.MAINTENANCE))
                .where('B', Predicates.blocks(GTBlocks.CASING_STEEL_GEARBOX.get()))
                .where('G', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                .where('P', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                .where('F', Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TungstenSteel).get()))
                .where('X', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get()))
                .where('O', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get())
                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS))
                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                )
                .where('W', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get())
                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                )
                .where('I', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                )
                // .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(90))
                .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                    .or(Predicates.blocks(PartAbility.INPUT_ENERGY.getBlockRange(1, GTValues.LuV).toArray(Block[]::new)).setMinGlobalLimited(1)))
                .where('M', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get())
                    .or(Predicates.abilities(PartAbility.MUFFLER)))
                // .where('X', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get()).setMinGlobalLimited(90)
                //     .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                //     .or(Predicates.autoAbilities(true, true, false)))
                .build();

            
            // non strict
            return FactoryBlockPattern.start()
                .aisle("CCCCCC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CCCCCC     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("CCCCCCCCCCC", "CF  F CXXXC", "CF  F CXXXC", "CF  F CXXXC", "CF  F CXXXC", "CCCCCCCXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "           ")
                .aisle("CCCCCCCCCCC", "C BB  X   X", "C BB  XPPPX", "C BB  X   X", "C BB  XPPPX", "CCCCCCX   X", "      XPPPX", "      X   X", "      XPPPX", "      X   X", "      XPPPX", "       XXX ")
                .aisle("CCCCCCCCCCC", "C BB  X   X", "C BB  XPPPX", "C BB  X   X", "C BB  XPPPX", "CCCCCCX   X", "      XPPPX", "      X   X", "      XPPPX", "      X   X", "      XPPPX", "       XXX ")
                .aisle("CCCCCCCCCCC", "CF  F CXXXC", "CF  F CXTXC", "CF  F CXXXC", "CF  F CXXXC", "CCCCCCCXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "           ")
                .aisle("CCCCCC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CCCCCC     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .where(' ', Predicates.any())
                .where('T', Predicates.controller(Predicates.blocks(definition.get())))
                .where('B', Predicates.blocks(GTBlocks.CASING_STEEL_GEARBOX.get()))
                .where('G', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                .where('P', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                .where('F', Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TungstenSteel).get()))
                // .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    .or(Predicates.autoAbilities(true, true, false))
                )
                // .where('X', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get()))
                .where('X', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get())
                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    .or(Predicates.autoAbilities(true, true, false))
                )
                .build();
            }
        )
        .shapeInfos((controller) -> {
            List<MultiblockShapeInfo> shapeInfos = new ArrayList<>();

            MultiblockShapeInfo.ShapeInfoBuilder builder = MultiblockShapeInfo.builder()
                .aisle("CCCCCC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CCCCCC     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("CCCCCCCCCCC", "CF  F CXXXC", "CF  F CXXXC", "CF  F CXXXC", "CF  F CXXXC", "CCCCCCCXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "           ")
                .aisle("CCCCCCCCCCC", "C BB  X   X", "C BB  XPPPX", "C BB  X   X", "C BB  XPPPX", "CCCCCCX   X", "      XPPPX", "      X   X", "      XPPPX", "      X   X", "      XPPPX", "       XXX ")
                .aisle("CCCCCCCCCCC", "C BB  X   X", "C BB  XPPPX", "C BB  X   X", "C BB  XPPPX", "CCCCCCX   X", "      XPPPX", "      X   X", "      XPPPX", "      X   X", "      XPPPX", "       XXX ")
                .aisle("CCCCCCCCCCC", "CF  F CXXXC", "CF  F CXTXC", "CF  F CXXXC", "CF  F CXXXC", "CCCCCCCXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "      CXXXC", "           ")
                .aisle("CCCCCC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CGGGGC     ", "CCCCCC     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .where(' ', Predicates.any())
                .where('T', Predicates.controller(Predicates.blocks(definition.get())))
                .where('B', Predicates.blocks(GTBlocks.CASING_STEEL_GEARBOX.get()))
                .where('G', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                .where('P', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                .where('F', Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TungstenSteel).get()))
                // // .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                // .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                //     .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                //     .or(Predicates.autoAbilities(true, true, false))
                // )
                // // .where('X', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get()))
                // .where('X', Predicates.blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get())
                //     .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                //     .or(Predicates.autoAbilities(true, true, false))
                // )
                .build();
                // .where('C', FusionReactorMachine.getCasingState(tier))
                // .where('G', FUSION_GLASS.get())
                // .where('K', FusionReactorMachine.getCoilState(tier))
                // .where('W', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.WEST)
                // .where('E', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.EAST)
                // .where('S', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.SOUTH)
                // .where('N', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.NORTH)
                // .where('w', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.WEST)
                // .where('e', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.EAST)
                // .where('s', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.SOUTH)
                // .where('n', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.NORTH)
                // .where('U', GTMachines.FLUID_IMPORT_HATCH[tier], Direction.UP)
                // .where('D', GTMachines.FLUID_IMPORT_HATCH[tier], Direction.DOWN)
                .where(' ', Blocks.AIR.defaultBlockState());

            // shapeInfos.add(baseBuilder.shallowCopy()
            //         .where('G', FusionReactorMachine.getCasingState(tier))
            //         .build());
            shapeInfos.add(builder.build());
            return shapeInfos;
        })
        .recoveryItems(() -> new ItemLike[] { GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
        .workableCasingRenderer(
            GTCEu.id("block/casings/solid/machine_casing_frost_proof"),
            GTCEu.id("block/multiblock/distillation_tower")
        )
        .register();

}
