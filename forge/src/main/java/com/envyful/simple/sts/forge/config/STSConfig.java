package com.envyful.simple.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.reforged.pixelmon.config.PokeSpecPricing;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Map;

@ConfigSerializable
@ConfigPath("config/SimpleSTS/config.yml")
public class STSConfig extends AbstractYamlConfig {

    private double minValue = 200.0;

    private Map<String, PokeSpecPricing> minPriceModifiers = ImmutableMap.of(
            "example", new PokeSpecPricing("shiny:1", new PokeSpecPricing.MathHandler("*", 2.0))
    );

    private List<String> blacklistSpecs = Lists.newArrayList("pikachu shiny:1");
    private transient List<PokemonSpec> blacklist = null;

    private PokeSpecPricing.MathHandler perLevelBooster = new PokeSpecPricing.MathHandler("+", 100);

    public STSConfig() {
        super();
    }

    public double getMinValue() {
        return this.minValue;
    }

    public Map<String, PokeSpecPricing> getMinPriceModifiers() {
        return this.minPriceModifiers;
    }

    public List<PokemonSpec> getBlacklist() {
        if (this.blacklist == null) {
            List<PokemonSpec> blackList = Lists.newArrayList();

            for (String blacklistSpec : this.blacklistSpecs) {
                blackList.add(PokemonSpec.from(blacklistSpec));
            }

            this.blacklist = blackList;
        }

        return this.blacklist;
    }

    public boolean isBlackListed(Pokemon pokemon) {
        for (PokemonSpec pokemonSpec : this.getBlacklist()) {
            if (pokemonSpec.matches(pokemon)) {
                return true;
            }
        }

        return false;
    }

    public double applyPerLevelBooster(double price, Pokemon pokemon) {
        if (pokemon == null) {
            return price;
        }

        if (this.perLevelBooster.getValue() == 0.0) {
            return price;
        }

        for (int i = 1; i <= pokemon.getLevel(); i++) {
            switch (this.perLevelBooster.getType().toLowerCase()) {
                default : case "+" :
                    price = price + this.perLevelBooster.getValue();
                    break;
                case "-" :
                    price = price - this.perLevelBooster.getValue();
                    break;
                case "*" :
                    price = price * this.perLevelBooster.getValue();
                    break;
                case "/" :
                    price = price / Math.max(0.00001, this.perLevelBooster.getValue());
                    break;
            }
        }

        return price;
    }


}
