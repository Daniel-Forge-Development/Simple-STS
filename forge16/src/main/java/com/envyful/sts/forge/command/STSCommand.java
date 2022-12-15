package com.envyful.sts.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.sts.forge.EnvySTSForge;
import com.envyful.sts.forge.ui.STSPartyUI;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;

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
    public void onCommand(@Sender ServerPlayerEntity player, String[] args) {
        if (StorageProxy.getParty(player).countAblePokemon() <= 1) {
            player.sendMessage(UtilChatColour.colour(EnvySTSForge.getLocale().getMinPartySize()), Util.NIL_UUID);
            return;
        }

        STSPartyUI.open(EnvySTSForge.getPlayerManager().getPlayer(player));
    }
}
