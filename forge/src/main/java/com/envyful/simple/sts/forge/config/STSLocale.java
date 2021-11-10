package com.envyful.simple.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.reforged.pixelmon.config.PokeSpecPricing;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@ConfigSerializable
@ConfigPath("config/SimpleSTS/locale.yml")
public class STSLocale extends AbstractYamlConfig {

    private String reloadedConfig = "&el(!) &eReloaded configs.";

    public STSLocale() {
        super();
    }

    public String getReloadedConfig() {
        return this.reloadedConfig;
    }
}
