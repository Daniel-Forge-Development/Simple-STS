package com.envyful.sts.forge.ui;

import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.concurrency.UtilForgeConcurrency;
import com.envyful.api.forge.config.UtilConfigInterface;
import com.envyful.api.forge.config.UtilConfigItem;
import com.envyful.api.forge.items.ItemBuilder;
import com.envyful.api.forge.items.ItemFlag;
import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.api.gui.pane.Pane;
import com.envyful.api.reforged.pixelmon.config.UtilPokemonPrice;
import com.envyful.api.reforged.pixelmon.sprite.UtilSprite;
import com.envyful.sts.forge.EnvySTSForge;
import com.envyful.sts.forge.config.STSConfig;
import com.envyful.sts.forge.config.STSGui;
import com.envyful.sts.forge.player.STSAttribute;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.enchantment.Enchantments;

import java.util.List;

public class STSPartyUI {

    private STSPartyUI() {
        throw new UnsupportedOperationException("Static GUI class");
    }

    public static void open(ForgeEnvyPlayer player) {
        STSGui.PartyUI config = EnvySTSForge.getGuis().getPartyUI();
        STSAttribute attribute = player.getAttribute(EnvySTSForge.class);
        PlayerPartyStorage party = StorageProxy.getParty(player.getParent());

        Pane pane = GuiFactory.paneBuilder()
                .topLeftX(0)
                .topLeftY(0)
                .width(9)
                .height(config.getGuiSettings().getHeight())
                .build();

        UtilConfigInterface.fillBackground(pane, config.getGuiSettings());
        party.retrieveAll();

        Pokemon[] all = party.getAll();

        setPokemon(player, pane);

        if (attribute.getSelectedSlot() != -1) {
            STSConfig globalConfig = EnvySTSForge.getConfig();
            double worth = UtilPokemonPrice.getMinPricePermissible(
                    player.getParent(),
                    all[attribute.getSelectedSlot()],
                    globalConfig.applyPerLevelBooster(globalConfig.getMinValue(), all[attribute.getSelectedSlot()]),
                    Lists.newArrayList(globalConfig.getMinPriceModifiers().values())
            );

            pane.set(config.getConfirmDisplay() % 9, config.getConfirmDisplay() / 9,
                    GuiFactory.displayableBuilder(new ItemBuilder(UtilSprite.getPokemonElement(
                                    all[attribute.getSelectedSlot()],
                                    config.getSpriteConfig()
                            ))
                                    .enchant(Enchantments.UNBREAKING, 1)
                                    .itemFlag(ItemFlag.HIDE_ENCHANTS)
                                    .addLore(getPriceLore(config, worth))
                                    .build())
                            .build()
            );
        }

        UtilConfigItem.builder()
                .combinedClickHandler(config.getConfirmItem(), (envyPlayer, clickType) -> {
                    STSConfig globalConfig = EnvySTSForge.getConfig();
                    double worth = UtilPokemonPrice.getMinPricePermissible(
                            player.getParent(),
                            all[attribute.getSelectedSlot()],
                            globalConfig.applyPerLevelBooster(globalConfig.getMinValue(), all[attribute.getSelectedSlot()]),
                            Lists.newArrayList(globalConfig.getMinPriceModifiers().values())
                    );

                    UtilForgeConcurrency.runSync(() -> {
                        player.getParent().closeContainer();

                        if (attribute.getSelectedSlot() == -1 || all.length <= attribute.getSelectedSlot()) {
                            return;
                        }

                        Pokemon pokemon = all[attribute.getSelectedSlot()];
                        party.set(attribute.getSelectedSlot(), null);
                        attribute.setSelectedSlot(-1);

                        BankAccount bank = BankAccountProxy.getBankAccountUnsafe(player.getParent());
                        bank.add((int)worth);

                        player.message(UtilChatColour.colour(
                                EnvySTSForge.getLocale().getSoldPokemon()
                                        .replace("%worth%", String.format(EnvySTSForge.getLocale().getEconomyFormat(), worth))
                                        .replace("%pokemon%", pokemon.getLocalizedName())
                                        .replace("%nickname%", pokemon.getDisplayName())
                        ));
                    });
                }).extendedConfigItem(player, pane, config.getConfirmItem());

        GuiFactory.guiBuilder()
                .setPlayerManager(EnvySTSForge.getPlayerManager())
                .addPane(pane)
                .height(config.getGuiSettings().getHeight())
                .title(UtilChatColour.colour(config.getGuiSettings().getTitle()))
                .build().open(player);
    }

    private static void setPokemon(ForgeEnvyPlayer player, Pane pane) {
        PlayerPartyStorage party = StorageProxy.getParty(player.getParent());
        Pokemon[] all = party.getAll();
        STSGui.PartyUI config = EnvySTSForge.getGuis().getPartyUI();

        for (int i = 0; i < 6; i++) {
            int pos = config.getPartySelectionPositions().get(i);

            if (i >= all.length || all[i] == null) {
                pane.set(pos % 9, pos / 9, GuiFactory.displayable(UtilConfigItem.fromConfigItem(config.getNoPokemonItem())));
            } else if (all[i].isUntradeable() || all[i].isEgg() || EnvySTSForge.getConfig().isBlackListed(all[i])) {
                pane.set(pos % 9, pos / 9, GuiFactory.displayable(UtilConfigItem.fromConfigItem(config.getUntradeablePokemonItem())));
            } else {
                final int slot = i;

                STSConfig globalConfig = EnvySTSForge.getConfig();
                double worth = UtilPokemonPrice.getMinPricePermissible(
                        player.getParent(),
                        all[i],
                        globalConfig.applyPerLevelBooster(globalConfig.getMinValue(), all[i]),
                        Lists.newArrayList(globalConfig.getMinPriceModifiers().values())
                );

                pane.set(pos % 9, pos / 9, GuiFactory.displayableBuilder(new ItemBuilder(UtilSprite.getPokemonElement(
                        all[i],
                        EnvySTSForge.getGuis().getPartyUI().getSpriteConfig()
                )).addLore(getPriceLore(config, worth)).build())
                        .clickHandler((envyPlayer, clickType) -> {
                            STSAttribute attribute = envyPlayer.getAttribute(EnvySTSForge.class);
                            attribute.setSelectedSlot(slot);
                            pane.set(config.getConfirmDisplay() % 9, config.getConfirmDisplay() / 9,
                                     GuiFactory.displayableBuilder(new ItemBuilder(UtilSprite.getPokemonElement(
                                             all[slot],
                                             config.getSpriteConfig()
                                     ))
                                                                           .enchant(Enchantments.UNBREAKING, 1)
                                                                           .itemFlag(ItemFlag.HIDE_ENCHANTS)
                                                                           .addLore(getPriceLore(config, worth))
                                                                           .build())
                                             .build()
                            );
                        }).build());
            }
        }
    }

    private static String[] getPriceLore(STSGui.PartyUI config, double worth) {
        List<String> lore = Lists.newArrayList();

        for (String s : config.getPriceLore()) {
            lore.add(UtilChatColour.translateColourCodes('&', s.replace("%cost%", String.format(EnvySTSForge.getLocale().getEconomyFormat(), worth))));
        }

        return lore.toArray(new String[0]);
    }
}
