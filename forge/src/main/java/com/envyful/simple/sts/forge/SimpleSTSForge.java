package com.envyful.simple.sts.forge;

import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.gui.factory.ForgeGuiFactory;
import com.envyful.api.forge.player.ForgePlayerManager;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.simple.sts.forge.command.STSCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
        modid = "simplests",
        name = "SimpleSTS Forge",
        version = SimpleSTSForge.VERSION,
        acceptableRemoteVersions = "*"
)
public class SimpleSTSForge {

    public static final String VERSION = "1.7.0";

    private static SimpleSTSForge instance;

    private ForgePlayerManager playerManager = new ForgePlayerManager();
    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    @Mod.EventHandler
    public void onServerStarting(FMLPreInitializationEvent event) {
        GuiFactory.setPlatformFactory(new ForgeGuiFactory());
        instance = this;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {

        this.commandFactory.registerCommand(event.getServer(), new STSCommand());
    }

    public static SimpleSTSForge getInstance() {
        return instance;
    }

    public ForgePlayerManager getPlayerManager() {
        return this.playerManager;
    }
}
