package com.envyful.sts.forge.config;

import com.envyful.api.reforged.pixelmon.config.PokeSpecPricing;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class DisplayablePokeSpecPricing extends PokeSpecPricing {

    private String display = "DISPLAYED TEXT!";

    public DisplayablePokeSpecPricing(String spec, MathHandler minPrice) {
        super(spec, minPrice);
    }

    public DisplayablePokeSpecPricing() {
    }

    public String getDisplay() {
        return this.display;
    }
}
