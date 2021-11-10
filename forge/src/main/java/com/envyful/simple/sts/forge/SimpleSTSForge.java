package com.envyful.simple.sts.forge;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.gui.factory.ForgeGuiFactory;
import com.envyful.api.forge.player.ForgePlayerManager;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.simple.sts.forge.command.STSCommand;
import com.envyful.simple.sts.forge.config.STSConfig;
import com.envyful.simple.sts.forge.config.STSLocale;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;

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

    private STSConfig config;
    private STSLocale locale;

    @Mod.EventHandler
    public void onServerStarting(FMLPreInitializationEvent event) {
        GuiFactory.setPlatformFactory(new ForgeGuiFactory());
        instance = this;

        this.loadConfig();
    }

    public void loadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(STSConfig.class);
            this.locale = YamlConfigFactory.getInstance(STSLocale.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public STSConfig getConfig() {
        return this.config;
    }

    public STSLocale getLocale() {
        return this.locale;
    }
}
