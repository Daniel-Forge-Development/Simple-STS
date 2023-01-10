package com.envyful.sts.forge.ui.placeholder;

import com.envyful.api.text.parse.MultiPlaceholder;
import com.envyful.sts.forge.EnvySTSForge;
import com.envyful.sts.forge.config.DisplayablePokeSpecPricing;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Collections;
import java.util.List;

public class PriceBreakdownPlaceholder implements MultiPlaceholder {

    private final ServerPlayerEntity player;
    private final Pokemon pokemon;
    private final List<DisplayablePokeSpecPricing> modifiers;

    public PriceBreakdownPlaceholder(ServerPlayerEntity player, Pokemon pokemon, List<DisplayablePokeSpecPricing> modifiers) {
        this.player = player;
        this.pokemon = pokemon;
        this.modifiers = modifiers;
    }


    @Override
    public List<String> replace(String s) {
        if (!s.contains("%price_breakdown%")) {
            return Collections.singletonList(s);
        }

        List<String> entries = Lists.newArrayList();

        for (DisplayablePokeSpecPricing minPriceModifier : this.modifiers) {
            if (!minPriceModifier.hasPermission(this.player)) {
                continue;
            }

            if (minPriceModifier.getSpec().matches(pokemon)) {
                entries.add(minPriceModifier.getDisplay());
            }
        }

        entries.add(EnvySTSForge.getLocale().getLevelModifierDisplay().replace("%added_cost%",
                String.format(EnvySTSForge.getLocale().getEconomyFormat(),
                        EnvySTSForge.getConfig().applyPerLevelBooster(EnvySTSForge.getConfig().getMinValue(), pokemon) - EnvySTSForge.getConfig().getMinValue()))
        );

        return entries;
    }
}
