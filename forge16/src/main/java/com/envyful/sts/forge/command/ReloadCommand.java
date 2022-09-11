package com.envyful.sts.forge.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.sts.forge.EnvySTSForge;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;

@Command(
        value = "reload",
        description = "Reloads the server configs"
)
@Child
@Permissible("com.envyful.sts.command.reload")
public class ReloadCommand {

    @CommandProcessor
    public void onCommand(@Sender ICommandSource sender, String[] args) {
        EnvySTSForge.getInstance().loadConfig();
        sender.sendMessage(UtilChatColour.colour(
                EnvySTSForge.getInstance().getLocale().getReloadedConfig()
        ), Util.NIL_UUID);
    }

}
