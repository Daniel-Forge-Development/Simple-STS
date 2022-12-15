package com.envyful.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/EnvySTS/locale.yml")
public class STSLocale extends AbstractYamlConfig {

    private String reloadedConfig = "&el(!) &eReloaded configs.";

    private String soldPokemon = "&e&l(!) &eSold %pokemon% for %worth%";

    private String minPartySize = "&c&l(!) &cYou must have at least 1 pokemon in your party to use STS";

    private String economyFormat = "%.2f";

    public STSLocale() {
        super();
    }

    public String getReloadedConfig() {
        return this.reloadedConfig;
    }

    public String getSoldPokemon() {
        return this.soldPokemon;
    }

    public String getMinPartySize() {
        return this.minPartySize;
    }

    public String getEconomyFormat() {
        return this.economyFormat;
    }
}
