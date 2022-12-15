package com.envyful.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigInterface;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.type.ExtendedConfigItem;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.reforged.pixelmon.config.SpriteConfig;
import com.envyful.api.type.Pair;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
@ConfigPath("config/EnvySTS/guis.yml")
public class STSGui extends AbstractYamlConfig {

    private PartyUI partyUI = new PartyUI();

    public STSGui() {
        super();
    }

    public PartyUI getPartyUI() {
        return this.partyUI;
    }

    @ConfigSerializable
    public static class PartyUI {

        private ConfigInterface guiSettings = new ConfigInterface(
                "Simple STS", 3, "BLOCK", ImmutableMap.of("one", new ConfigItem(
                "minecraft:stained_glass_pane", 1, (byte) 15, " ",
                Lists.newArrayList(), Maps.newHashMap()
        )));

        private List<Integer> partySelectionPositions = Lists.newArrayList(
                10, 11, 12, 14, 15, 16
        );

        private int confirmDisplay = 13;

        private ExtendedConfigItem confirmItem = ExtendedConfigItem.builder()
                .type("pixelmon:poke_ball")
                .amount(1)
                .name("&a&lCONFIRM")
                .positions(Pair.of(4, 2))
                .build();

        private ConfigItem noPokemonItem = ConfigItem.builder()
                .type("minecraft:barrier")
                .name("&cNo pokemon in this position")
                .amount(1)
                .build();

        private ConfigItem untradeablePokemonItem = ConfigItem.builder()
                .type("minecraft:barrier")
                .name("&c&lUNTRADEABLE")
                .amount(1)
                .build();

        private SpriteConfig spriteConfig = new SpriteConfig();

        private List<String> priceLore = Lists.newArrayList(
                "&e&lPRICE: &a$%cost%"
        );

        public SpriteConfig getSpriteConfig() {
            return this.spriteConfig;
        }

        public ConfigItem getUntradeablePokemonItem() {
            return this.untradeablePokemonItem;
        }

        public ConfigInterface getGuiSettings() {
            return this.guiSettings;
        }

        public List<Integer> getPartySelectionPositions() {
            return this.partySelectionPositions;
        }

        public int getConfirmDisplay() {
            return this.confirmDisplay;
        }

        public ExtendedConfigItem getConfirmItem() {
            return this.confirmItem;
        }

        public ConfigItem getNoPokemonItem() {
            return this.noPokemonItem;
        }

        public List<String> getPriceLore() {
            return this.priceLore;
        }
    }

}
