package com.envyful.simple.sts.forge.ui;

import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.concurrency.UtilForgeConcurrency;
import com.envyful.api.forge.config.UtilConfigItem;
import com.envyful.api.forge.items.ItemBuilder;
import com.envyful.api.forge.items.ItemFlag;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.api.gui.pane.Pane;
import com.envyful.api.player.EnvyPlayer;
import com.envyful.api.reforged.pixelmon.config.UtilPokemonPrice;
import com.envyful.api.reforged.pixelmon.sprite.UtilSprite;
import com.envyful.api.reforged.pixelmon.storage.UtilPixelmonPlayer;
import com.envyful.simple.sts.forge.SimpleSTSForge;
import com.envyful.simple.sts.forge.config.STSConfig;
import com.envyful.simple.sts.forge.config.STSGui;
import com.envyful.simple.sts.forge.player.STSAttribute;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;

import java.util.List;

public class STSPartyUI {

    public static void open(EnvyPlayer<EntityPlayerMP> player) {
        STSGui.PartyUI config = SimpleSTSForge.getInstance().getGuis().getPartyUI();
        STSAttribute attribute = player.getAttribute(SimpleSTSForge.class);

        Pane pane = GuiFactory.paneBuilder()
                .topLeftX(0)
                .topLeftY(0)
                .width(9)
                .height(config.getGuiSettings().getHeight())
                .build();

        for (ConfigItem fillerItem : config.getGuiSettings().getFillerItems()) {
            pane.add(GuiFactory.displayable(UtilConfigItem.fromConfigItem(fillerItem)));
        }

        PlayerPartyStorage party = UtilPixelmonPlayer.getParty(player.getParent());

        party.retrieveAll();

        Pokemon[] all = party.getAll();

        setPokemon(player, pane);

        if (attribute.getSelectedSlot() != -1) {
            STSConfig globalConfig = SimpleSTSForge.getInstance().getConfig();
            double worth = UtilPokemonPrice.getMinPrice(
                    all[attribute.getSelectedSlot()],
                    globalConfig.getMinValue(),
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

        UtilConfigItem.addConfigItem(pane, config.getConfirmItem(), (envyPlayer, clickType) -> {
            STSConfig globalConfig = SimpleSTSForge.getInstance().getConfig();
            double worth = UtilPokemonPrice.getMinPrice(
                    all[attribute.getSelectedSlot()],
                    globalConfig.getMinValue(),
                    Lists.newArrayList(globalConfig.getMinPriceModifiers().values())
            );

            UtilForgeConcurrency.runSync(() -> {
                player.getParent().closeScreen();
                Pokemon pokemon = all[attribute.getSelectedSlot()];
                party.set(attribute.getSelectedSlot(), null);

                IPixelmonBankAccount bank = Pixelmon.moneyManager.getBankAccountUnsafe(player.getParent());
                bank.changeMoney((int) worth);

                player.message(UtilChatColour.translateColourCodes(
                        '&',
                        SimpleSTSForge.getInstance().getLocale().getSoldPokemon()
                                .replace("%worth%", String.format("%.2f", worth))
                                .replace("%pokemon%", pokemon.getLocalizedName())
                                .replace("%nickname%", pokemon.getDisplayName())
                ));
            });
        });

        GuiFactory.guiBuilder()
                .setPlayerManager(SimpleSTSForge.getInstance().getPlayerManager())
                .addPane(pane)
                .setCloseConsumer(envyPlayer -> {})
                .height(config.getGuiSettings().getHeight())
                .title(UtilChatColour.translateColourCodes('&', config.getGuiSettings().getTitle()))
                .build().open(player);
    }

    private static void setPokemon(EnvyPlayer<EntityPlayerMP> player, Pane pane) {
        PlayerPartyStorage party = UtilPixelmonPlayer.getParty(player.getParent());
        Pokemon[] all = party.getAll();
        STSGui.PartyUI config = SimpleSTSForge.getInstance().getGuis().getPartyUI();

        for (int i = 0; i < 6; i++) {
            int pos = config.getPartySelectionPositions().get(i);

            if (i >= all.length || all[i] == null) {
                pane.set(pos % 9, pos / 9, GuiFactory.displayable(UtilConfigItem.fromConfigItem(config.getNoPokemonItem())));
            } else if (all[i].hasSpecFlag("untradeable") || all[i].isEgg() || SimpleSTSForge.getInstance().getConfig().isBlackListed(all[i])) {
                pane.set(pos % 9, pos / 9, GuiFactory.displayable(UtilConfigItem.fromConfigItem(config.getUntradeablePokemonItem())));
            } else {
                final int slot = i;

                STSConfig globalConfig = SimpleSTSForge.getInstance().getConfig();
                double worth = UtilPokemonPrice.getMinPrice(
                        all[i],
                        globalConfig.getMinValue(),
                        Lists.newArrayList(globalConfig.getMinPriceModifiers().values())
                );

                pane.set(pos % 9, pos / 9, GuiFactory.displayableBuilder(new ItemBuilder(UtilSprite.getPokemonElement(
                        all[i],
                        SimpleSTSForge.getInstance().getGuis().getPartyUI().getSpriteConfig()
                )).addLore(getPriceLore(config, worth)).build())
                        .clickHandler((envyPlayer, clickType) -> {
                            STSAttribute attribute = envyPlayer.getAttribute(SimpleSTSForge.class);
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
            lore.add(UtilChatColour.translateColourCodes('&', s.replace("%cost%", String.format("%.2f", worth))));
        }

        return lore.toArray(new String[0]);
    }
}
