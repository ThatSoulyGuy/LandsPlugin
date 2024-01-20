package com.thatsoulyguy.landsplugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.thatsoulyguy.landsplugin.command.CommandManager;
import com.thatsoulyguy.landsplugin.command.HubCommand;
import com.thatsoulyguy.landsplugin.config.LConfig;
import com.thatsoulyguy.landsplugin.event.PlayerEvents;
import com.thatsoulyguy.landsplugin.world.LWorldManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LandsPlugin extends JavaPlugin
{
    public LConfig landsConfig = new LConfig();

    public static MultiverseCore MultiverseCore = null;
    public static MVWorldManager WorldManager = null;

    public static LandsPlugin Instance = null;

    @Override
    public void onEnable()
    {
        Instance = this;

        landsConfig.Initialize("playerdata.yml");
        LWorldManager.InitializeWorlds();

        CommandManager.Initialize();

        Objects.requireNonNull(getCommand("hub")).setExecutor(new HubCommand());
        Objects.requireNonNull(getCommand("lands")).setExecutor(new CommandManager());

        MultiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);

        assert MultiverseCore != null;
        WorldManager = MultiverseCore.getMVWorldManager();
    }

    @Override
    public void onDisable()
    {

    }
}
