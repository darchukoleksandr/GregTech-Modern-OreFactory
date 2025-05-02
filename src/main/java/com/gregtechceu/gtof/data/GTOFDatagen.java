package com.gregtechceu.gtof.data;

import com.gregtechceu.gtof.registry.GTOFRegistries;
import com.gregtechceu.gtof.data.lang.LangHandler;

import com.tterrag.registrate.providers.ProviderType;

public class GTOFDatagen {
    public static void init() {
        GTOFRegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}