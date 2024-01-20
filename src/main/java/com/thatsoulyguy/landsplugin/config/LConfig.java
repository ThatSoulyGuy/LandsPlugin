package com.thatsoulyguy.landsplugin.config;

import com.thatsoulyguy.landsplugin.LandsPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LConfig
{
    private File configFile;
    private FileConfiguration config;
    private String path;

    public void Initialize(String path)
    {
        this.path = path;

        configFile = new File(LandsPlugin.Instance.getDataFolder(), path);

        if (!configFile.exists())
        {
            configFile.getParentFile().mkdirs();
            LandsPlugin.Instance.saveResource(path, false);
        }

        config = new YamlConfiguration();

        try
        {
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public void Reload()
    {
        configFile = new File(LandsPlugin.Instance.getDataFolder(), path);

        if (!configFile.exists())
        {
            configFile.getParentFile().mkdirs();
            LandsPlugin.Instance.saveResource(path, false);
        }

        config = new YamlConfiguration();

        try
        {
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public void SetValue(String path, Object value)
    {
        config.set(path, value);
    }

    public Object GetValue(String path)
    {
        return config.get(path);
    }

    public List<String> GetAll(String path)
    {
        ConfigurationSection configurationSection = config.getConfigurationSection(path);

        if(configurationSection != null)
        {
            Set<String> keys = configurationSection.getKeys(false);

            if(keys != null)
                return keys.stream().toList();
        }

        return new ArrayList<>();
    }

    public void RemoveKey(String path)
    {
        if (config.contains(path))
            config.set(path, null);

        Save();
    }

    public void Save()
    {
        try
        {
            config.save(configFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}