package com.envyful.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigInterface;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.type.PositionableConfigItem;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.reforged.pixelmon.config.SpriteConfig;
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

        private PositionableConfigItem confirmItem = new PositionableConfigItem(
                "pixelmon:poke_ball", 1, (byte) 0, "&a&lCONFIRM",
                Lists.newArrayList(), 4, 2, Maps.newHashMap()
        );

        private ConfigItem noPokemonItem = new ConfigItem(
                "minecraft:barrier",
                1, (byte) 0, "&cNo pokemon in this position",
                Lists.newArrayList(), Maps.newHashMap()
        );

        private ConfigItem untradeablePokemonItem = new ConfigItem(
                "minecraft:barrier",
                1, (byte) 0, "&c&lUNTRADEABLE",
                Lists.newArrayList(), Maps.newHashMap()
        );

        private SpriteConfig spriteConfig = new SpriteConfig();

        private List<String> priceLore = Lists.newArrayList(
                "&e&lPRICE: &a$%cost%"
        );

        public PartyUI() {
        }

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

        public PositionableConfigItem getConfirmItem() {
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
