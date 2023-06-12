package com.envyful.sts.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigInterface;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.type.ExtendedConfigItem;
import com.envyful.api.config.type.PaginatedConfigInterface;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.reforged.pixelmon.config.SpriteConfig;
import com.envyful.api.type.Pair;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.lwjgl.system.CallbackI;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
@ConfigPath("config/EnvySTS/guis.yml")
public class STSGui extends AbstractYamlConfig {

    private PartyUI partyUI = new PartyUI();
    private PcUI pcBulkSellUI = new PcUI();

    public STSGui() {
        super();
    }

    public PartyUI getPartyUI() {
        return this.partyUI;
    }

    public PcUI getPcBulkSellUI() {
        return this.pcBulkSellUI;
    }

    @ConfigSerializable
    public static class PartyUI {

        private ConfigInterface guiSettings = new ConfigInterface(
                "Simple STS", 3, "BLOCK", ImmutableMap.of("one", ConfigItem.builder()
                .type("minecraft:black_stained_glass_pane").amount(1).name(" ").build()));

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

        private ExtendedConfigItem pcItem = ExtendedConfigItem.builder()
                .type("minecraft:barrier")
                .name("&c&lPC ITEM")
                .positions(Pair.of(1, 2))
                .amount(1)
                .build();

        private SpriteConfig spriteConfig = new SpriteConfig();

        private List<String> priceLore = Lists.newArrayList(
                "&e&lPRICE: &a$%cost%",
                "%price_breakdown%"
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

        public ExtendedConfigItem getPcItem() {
            return this.pcItem;
        }
    }

    @ConfigSerializable
    public static class PcUI {

        private PaginatedConfigInterface guiSettings = PaginatedConfigInterface.builder()
                .title("Bulk Sell PC")
                .height(6)
                .displayPageButtonsAtLimits()
                .nextPageButton(ExtendedConfigItem.builder()
                        .type("minecraft:stone")
                        .positions(Pair.of(0, 0))
                        .name("Next Page")
                        .build())
                .previousPageButton(ExtendedConfigItem.builder()
                        .type("minecraft:stone")
                        .positions(Pair.of(1, 0))
                        .name("Previous Page")
                        .build())
                .build();

        private SpriteConfig spriteConfig = new SpriteConfig();

        private ExtendedConfigItem backButton = ExtendedConfigItem.builder()
                .type("minecraft:stone")
                .positions(Pair.of(3, 0))
                .name("Back")
                .build();

        public PcUI() {
        }

        public PaginatedConfigInterface getGuiSettings() {
            return this.guiSettings;
        }

        public ExtendedConfigItem getBackButton() {
            return this.backButton;
        }

        public SpriteConfig getSpriteConfig() {
            return this.spriteConfig;
        }
    }
}
