package com.envyful.simple.sts.forge.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.simple.sts.forge.SimpleSTSForge;
import net.minecraft.command.ICommandSender;

@Command(
        value = "reload",
        description = "Reloads the server configs"
)
@Child
@Permissible("simple.sts.forge.command.reload")
public class ReloadCommand {

    @CommandProcessor
    public void onCommand(@Sender ICommandSender sender, String[] args) {
        SimpleSTSForge.getInstance().loadConfig();
        //TODO: send message from locale
    }

}
