package com.envyful.sts.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.reforged.pixelmon.storage.UtilPixelmonPlayer;
import com.envyful.sts.forge.EnvySTSForge;
import com.envyful.sts.forge.ui.STSPartyUI;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

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
    public void onCommand(@Sender EntityPlayerMP player, String[] args) {
        if (UtilPixelmonPlayer.getParty(player).countAblePokemon() <= 1) {
            player.sendMessage(new TextComponentString(UtilChatColour.translateColourCodes(
                    '&',
                    EnvySTSForge.getInstance().getLocale().getMinPartySize()
            )));
            return;
        }

        STSPartyUI.open(EnvySTSForge.getInstance().getPlayerManager().getPlayer(player));
    }
}
