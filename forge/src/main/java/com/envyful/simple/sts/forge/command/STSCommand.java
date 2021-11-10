package com.envyful.simple.sts.forge.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import net.minecraft.entity.player.EntityPlayerMP;

@Command(
        value = "sts",
        description = "Opens the STS GUI",
        aliases = {
                "simplests",
                "ssts",
                "selltoserver"
        }
)
@Permissible("simple.sts.forge.command.sts")
@SubCommands(
        ReloadCommand.class
)
public class STSCommand {

    @CommandProcessor
    public void onCommand(@Sender EntityPlayerMP player, String[] args) {
        //TODO: open UI
    }
}
