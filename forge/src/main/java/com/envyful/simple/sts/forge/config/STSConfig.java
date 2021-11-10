package com.envyful.simple.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.reforged.pixelmon.config.PokeSpecPricing;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@ConfigSerializable
@ConfigPath("config/SimpleSTS/config.yml")
public class STSConfig extends AbstractYamlConfig {

    private Map<String, PokeSpecPricing> minPriceModifiers = ImmutableMap.of(
            "example", new PokeSpecPricing("shiny:1", new PokeSpecPricing.MathHandler("*", 2.0))
    );

    public STSConfig() {
        super();
    }

    public Map<String, PokeSpecPricing> getMinPriceModifiers() {
        return this.minPriceModifiers;
    }
}
