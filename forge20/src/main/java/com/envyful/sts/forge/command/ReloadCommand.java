package com.envyful.sts.forge.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.sts.forge.EnvySTSForge;
import net.minecraft.commands.CommandSource;

@Command(
        value = "reload",
        description = "Reloads the server configs"
)
@Child
@Permissible("com.envyful.sts.command.reload")
public class ReloadCommand {

    @CommandProcessor
    public void onCommand(@Sender CommandSource sender, String[] args) {
        EnvySTSForge.loadConfig();
        sender.sendSystemMessage(UtilChatColour.colour(EnvySTSForge.getLocale().getReloadedConfig()));
    }
}
