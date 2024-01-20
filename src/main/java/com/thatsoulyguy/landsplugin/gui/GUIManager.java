package com.thatsoulyguy.landsplugin.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GUIManager
{
    private static final Map<String, Inventory> guis = new HashMap<>();

    public static String FormatChatColor(String input)
    {
        return input.replace('&', ChatColor.COLOR_CHAR);
    }

    public static void Initialize()
    {
        guis.put("lands", Bukkit.createInventory(null, 27, FormatChatColor("&7&lLands Menu")));

        ItemStack grayPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        ItemMeta meta = grayPane.getItemMeta();

        assert meta != null;
        meta.setDisplayName("");

        grayPane.setItemMeta(meta);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

        ItemStack[] guiItems = GetLandsItems(grayPane, barrier, playerHead);

        guis.get("lands").setContents(guiItems);
    }

    public static void OpenGUI(Player player, String name)
    {
        player.openInventory(guis.get(name));
    }

    @NotNull
    private static ItemStack[] GetLandsItems(ItemStack grayPane, ItemStack barrier, ItemStack playerHead)
    {
        ItemStack limeConcrete = new ItemStack(Material.LIME_CONCRETE);
        ItemStack darkOakDoor = new ItemStack(Material.DARK_OAK_DOOR);

        return new ItemStack[]
        {
            grayPane, grayPane, grayPane, grayPane, grayPane, grayPane, grayPane, grayPane, grayPane,
            grayPane, grayPane, barrier, grayPane, grayPane, playerHead, limeConcrete, grayPane, grayPane,
            darkOakDoor, grayPane, grayPane, grayPane, grayPane, grayPane, grayPane, grayPane, grayPane
        };
    }
}