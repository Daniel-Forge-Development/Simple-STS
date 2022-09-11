package com.envyful.sts.forge;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.gui.factory.ForgeGuiFactory;
import com.envyful.api.forge.player.ForgePlayerManager;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.sts.forge.command.STSCommand;
import com.envyful.sts.forge.config.STSConfig;
import com.envyful.sts.forge.config.STSGui;
import com.envyful.sts.forge.config.STSLocale;
import com.envyful.sts.forge.player.STSAttribute;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;

@Mod(
        modid = "envysts",
        name = "EnvySTS Forge",
        version = EnvySTSForge.VERSION,
        acceptableRemoteVersions = "*"
)
public class EnvySTSForge {

    public static final String VERSION = "2.0.0";

    private static EnvySTSForge instance;

    private ForgePlayerManager playerManager = new ForgePlayerManager();
    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    private STSConfig config;
    private STSLocale locale;
    private STSGui guis;

    @Mod.EventHandler
    public void onServerStarting(FMLPreInitializationEvent event) {
        GuiFactory.setPlatformFactory(new ForgeGuiFactory());
        instance = this;

        this.playerManager.registerAttribute(this, STSAttribute.class);

        this.loadConfig();
    }

    public void loadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(STSConfig.class);
            this.locale = YamlConfigFactory.getInstance(STSLocale.class);
            this.guis = YamlConfigFactory.getInstance(STSGui.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {

        this.commandFactory.registerCommand(event.getServer(), new STSCommand());
    }

    public static EnvySTSForge getInstance() {
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

    public STSGui getGuis() {
        return this.guis;
    }
}
