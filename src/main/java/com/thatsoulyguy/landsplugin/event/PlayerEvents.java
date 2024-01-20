package com.thatsoulyguy.landsplugin.event;

import com.thatsoulyguy.landsplugin.LandsPlugin;
import com.thatsoulyguy.landsplugin.world.LWorldManager;
import com.thatsoulyguy.landsplugin.world.WorldAccessFlags;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class PlayerEvents implements Listener
{
    @EventHandler
    public void OnPlayerJoined(PlayerJoinEvent event)
    {
        if(!LWorldManager.WorldExists("land-" + event.getPlayer()))
            LWorldManager.CreateWorld(event.getPlayer(), WorldAccessFlags.DENY_BLOCK_BREAK, WorldAccessFlags.DENY_BLOCK_PLACE, WorldAccessFlags.PRIVATE);

        LWorldManager.TeleportPlayer(event.getPlayer(), "land-" + event.getPlayer().getName());
    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event)
    {
        Block block = event.getBlock();

        String world = block.getWorld().getName();

        if(world.equalsIgnoreCase("world"))
            return;

        EnumSet<WorldAccessFlags> worldFlags = WorldAccessFlags.Decompose(Integer.parseInt((String) LandsPlugin.Instance.landsConfig.GetValue("worlds." + world + ".flags")));

        List<String> invitedPlayers = LandsPlugin.Instance.landsConfig.GetAll("worlds." + world + ".invitedplayers");

        if (!worldFlags.contains(WorldAccessFlags.ALLOW_BLOCK_BREAK) || !invitedPlayers.contains(event.getPlayer().getName()) || !((String)LandsPlugin.Instance.landsConfig.GetValue("worlds." + world + ".owner")).equalsIgnoreCase(event.getPlayer().getName()))
            event.setCancelled(true);
    }

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event)
    {
        Block block = event.getBlock();

        String world = block.getWorld().getName();

        if(world.equalsIgnoreCase("world"))
            return;

        EnumSet<WorldAccessFlags> worldFlags = WorldAccessFlags.Decompose(Integer.parseInt((String) LandsPlugin.Instance.landsConfig.GetValue("worlds." + world + ".flags")));

        List<String> invitedPlayers = LandsPlugin.Instance.landsConfig.GetAll("worlds." + world + ".invitedplayers");

        if(!worldFlags.contains(WorldAccessFlags.ALLOW_BLOCK_PLACE) || !invitedPlayers.contains(event.getPlayer().getName()) || !((String)LandsPlugin.Instance.landsConfig.GetValue("worlds." + world + ".owner")).equalsIgnoreCase(event.getPlayer().getName()))
            event.setCancelled(true);
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event)
    {
        Block block = event.getClickedBlock();

        if(block == null)
            return;

        String world = block.getWorld().getName();

        if(world.equalsIgnoreCase("world"))
            return;

        List<String> invitedPlayers = LandsPlugin.Instance.landsConfig.GetAll("worlds." + world + ".invitedplayers");

        if(!invitedPlayers.contains(event.getPlayer().getName()) || !((String)LandsPlugin.Instance.landsConfig.GetValue("worlds." + world + ".owner")).equalsIgnoreCase(event.getPlayer().getName()))
            event.setCancelled(true);
    }
}
