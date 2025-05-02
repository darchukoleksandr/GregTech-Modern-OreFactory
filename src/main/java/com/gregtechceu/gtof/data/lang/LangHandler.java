package com.gregtechceu.gtof.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;
// import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // initItemTooltips(provider);
    }

    private static void initItemTooltips(RegistrateLangProvider provider) {
        //provider.add(material.getUnlocalizedName(), toEnglishName(material.getName()));

        // provider.add("gtceu.ore_factory_1", "Ore 1");
        // provider.add("gtceu.multiblock.ore_factory_descr", "Ore descr");
    }
}
