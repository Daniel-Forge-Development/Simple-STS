package com.envyful.sts.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.sts.forge.EnvySTSForge;
import com.envyful.sts.forge.player.STSAttribute;
import com.envyful.sts.forge.ui.STSPartyUI;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;

@Command(
        value = "sts",
        description = "Opens the STS GUI",
        aliases = {
                "envysts",
                "ests",
                "selltoserver"
        }
)
@Permissible("com.envyful.command.sts")
@SubCommands(
        ReloadCommand.class
)
public class STSCommand {

    @CommandProcessor
    public void onCommand(@Sender ServerPlayerEntity source, String[] args) {
        ForgeEnvyPlayer player = EnvySTSForge.getInstance().getPlayerManager().getPlayer(source);
        STSAttribute attribute = player.getAttribute(EnvySTSForge.class);
        if (attribute == null) {
            return;
        }

        if (!attribute.canSell()) {
            player.message(UtilChatColour.colour(EnvySTSForge.getInstance().getLocale().getOnCooldown()
                    .replace("%cooldown%", attribute.getCooldownFormatted()))
            );
            return;
        }

        if (StorageProxy.getParty(source).countAblePokemon() <= 1) {
            player.message(UtilChatColour.colour(
                    EnvySTSForge.getInstance().getLocale().getMinPartySize())
            );
            return;
        }

        STSPartyUI.open(player);
    }
}
