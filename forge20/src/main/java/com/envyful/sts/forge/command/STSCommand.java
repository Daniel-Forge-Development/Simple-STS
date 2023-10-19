package com.envyful.sts.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.sts.forge.EnvySTSForge;
import com.envyful.sts.forge.player.STSAttribute;
import com.envyful.sts.forge.ui.STSPartyUI;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.server.level.ServerPlayer;

@Command(
        value = {
                "sts",
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
    public void onCommand(@Sender ServerPlayer player, String[] args) {
        ForgeEnvyPlayer sender = EnvySTSForge.getPlayerManager().getPlayer(player);
        STSAttribute attribute = sender.getAttribute(STSAttribute.class);

        if (attribute.onCooldown()) {
            sender.message(EnvySTSForge.getLocale().getCooldown().replace("%cooldown%", attribute.getRemainingTime()));
            return;
        }

        if (StorageProxy.getPartyNow(player).countAblePokemon() <= 1) {
            player.sendSystemMessage(UtilChatColour.colour(EnvySTSForge.getLocale().getMinPartySize()));
            return;
        }

        STSPartyUI.open(sender);
    }
}
