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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.io.IOException;

@Mod("envysts")
public class EnvySTSForge {

    public static final String VERSION = "2.0.0";

    private static EnvySTSForge instance;

    private ForgePlayerManager playerManager = new ForgePlayerManager();
    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    private STSConfig config;
    private STSLocale locale;
    private STSGui guis;

    public EnvySTSForge() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerAboutToStartEvent event) {
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

    @SubscribeEvent
    public void onServerStarting(RegisterCommandsEvent event) {
        this.commandFactory.registerCommand(event.getDispatcher(), new STSCommand());
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
