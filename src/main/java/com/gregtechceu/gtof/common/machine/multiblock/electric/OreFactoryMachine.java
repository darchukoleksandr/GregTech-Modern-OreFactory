package com.gregtechceu.gtof.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import com.gregtechceu.gtof.config.ConfigHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.*;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OreFactoryMachine extends WorkableElectricMultiblockMachine {

    public OreFactoryMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        MultiblockDisplayText.builder(textList, isFormed()).addParallelsLine(ConfigHolder.INSTANCE.machines.oreFactory.parallels);
        super.addDisplayText(textList);
    }

    // @Override
    // public ModularUI createUI(Player entityPlayer) {
    //     ModularUI modularUI = super.createUI(entityPlayer);
    //     // modularUI.registerCloseListener(() -> {
    //     //     Block.popResource(getLevel(), getPos(), posSaveSlot.getStackInSlot(0));
    //     // });
    //     // modularUI.widget(new SlotWidget(posSaveSlot, 0, 149, 105));
    //     // modularUI.widget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()));
    //     // modularUI.widget(new ButtonWidget(129, 105, 18, 18, this::onSaveButtonClick)
    //     //         .setButtonTexture(GuiTextures.BUTTON)
    //     //         .setHoverTooltips(Component.translatable("menu.gcyr.save_destination_position")));
    //     return modularUI;
    // }

    // private void onSaveButtonClick(ClickData data) {
    //     // if (data.isRemote) return;
    //     com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] onSaveButtonClick");

    //     // ItemStack saveStack = this.posSaveSlot.getStackInSlot(0);
    //     // if (GCYRItems.ID_CHIP.isIn(saveStack)) {
    //     //     Direction back = this.getFrontFacing().getOpposite();
    //     //     BlockPos landPos = getPos().relative(back, bDist / 2);
    //     //     ResourceKey<Level> level = this.getLevel().dimension();
    //     //     PlanetIdChipBehaviour.setSavedPosition(saveStack, level, landPos);
    //     // }
    // }
    
    // @Override
    // public boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
    //     // Component component = capability.getName();
    //     // String string = component.getString(); // item
    //     // com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] canVoidRecipeOutputs string: " + string);
    //     Object2IntMap<RecipeCapability<?>> recipeOutputLimits = self().getDefinition().getRecipeOutputLimits();
    //     // for (var recipeOutputLimit : recipeOutputLimits.entrySet()) {
    //     //     com.gregtechceu.gtof.GTOreFactory.LOGGER.info("[GregtechOreFactory] canVoidRecipeOutputs for name: " + recipeOutputLimit.getKey().name);
    //     // }
    //     return recipeOutputLimits.containsKey(capability);
    //     // return self().getDefinition().getRecipeOutputLimits().containsKey(capability);
    //     // return capability != EURecipeCapability.CAP;
    // }
}
