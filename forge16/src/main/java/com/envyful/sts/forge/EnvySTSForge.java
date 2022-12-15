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

    private static EnvySTSForge instance;

    private final ForgePlayerManager playerManager = new ForgePlayerManager();
    private final ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    private STSConfig config;
    private STSLocale locale;
    private STSGui guis;

    public EnvySTSForge() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerAboutToStartEvent event) {
        GuiFactory.setPlatformFactory(new ForgeGuiFactory());
        loadConfig();
    }

    public static void loadConfig() {
        try {
            instance.config = YamlConfigFactory.getInstance(STSConfig.class);
            instance.locale = YamlConfigFactory.getInstance(STSLocale.class);
            instance.guis = YamlConfigFactory.getInstance(STSGui.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onServerStarting(RegisterCommandsEvent event) {
        this.playerManager.registerAttribute(this, STSAttribute.class);
        this.commandFactory.registerCommand(event.getDispatcher(), new STSCommand());
    }

    public static ForgePlayerManager getPlayerManager() {
        return instance.playerManager;
    }

    public static STSConfig getConfig() {
        return instance.config;
    }

    public static STSLocale getLocale() {
        return instance.locale;
    }

    public static STSGui getGuis() {
        return instance.guis;
    }
}
