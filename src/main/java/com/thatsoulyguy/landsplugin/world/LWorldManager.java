package com.thatsoulyguy.landsplugin.world;

import com.thatsoulyguy.landsplugin.LandsPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class LWorldManager
{
    private static final Map<String, World> registeredWorlds = new HashMap<>();

    public static void InitializeWorlds()
    {
        for (String key : LandsPlugin.Instance.landsConfig.GetAll("worlds"))
        {
            String name = (String) LandsPlugin.Instance.landsConfig.GetValue("worlds." + key + ".name");

            registeredWorlds.put(name, Bukkit.getWorld(name));
        }
    }

    public static void CreateWorld(Player player, WorldAccessFlags... flags)
    {
        String worldName = "land-" + player.getName();

        WorldCreator worldCreator = new WorldCreator(worldName);

        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.NORMAL);

        LandsPlugin.WorldManager.addWorld(worldName, World.Environment.NORMAL, null, WorldType.NORMAL, false, null);

        World world = Bukkit.getWorld(worldName);

        if(world != null)
        {
            world.getWorldBorder().setCenter(world.getSpawnLocation());
            world.getWorldBorder().setSize(1024);
        }

        LandsPlugin.Instance.landsConfig.SetValue("worlds." + worldName + ".name", worldName);
        LandsPlugin.Instance.landsConfig.SetValue("worlds." + worldName + ".owner", player.getName());
        LandsPlugin.Instance.landsConfig.SetValue("worlds." + worldName + ".flags", WorldAccessFlags.Combine(flags));

        LandsPlugin.Instance.landsConfig.Save();

        registeredWorlds.put(worldName, world);
    }

    public static void TeleportPlayer(Player player, String name)
    {
        World world = Bukkit.getWorld(name);

        if(world == null)
            return;

        player.teleport(new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
    }

    public static boolean WorldExists(String name)
    {
        return Bukkit.getWorld(name) != null;
    }

    public static void InvitePlayer(Player player, String name)
    {
        List<String> invitedPlayers = LandsPlugin.Instance.landsConfig.GetAll("worlds." + name + ".invitedplayers");

        if (invitedPlayers == null || invitedPlayers.isEmpty())
            invitedPlayers = new ArrayList<>();

        invitedPlayers.add(player.getName());

        LandsPlugin.Instance.landsConfig.SetValue("worlds." + name + ".invitedplayers", invitedPlayers);
        LandsPlugin.Instance.landsConfig.Save();
    }

    public static boolean PlayerAllowed(Player player, String name)
    {
        List<String> invitedPlayers = LandsPlugin.Instance.landsConfig.GetAll("worlds." + name + ".invitedplayers");

        if (invitedPlayers == null || invitedPlayers.isEmpty())
            return false;

        EnumSet<WorldAccessFlags> flags = WorldAccessFlags.Decompose(Integer.parseInt((String)LandsPlugin.Instance.landsConfig.GetValue("worlds." + player.getName() + ".flags")));

        if(invitedPlayers.contains(player.getName()) || flags.contains(WorldAccessFlags.PUBLIC))
            return true;

        return true;
    }

    public static World GetWorld(String name)
    {
        return registeredWorlds.get(name);
    }
}