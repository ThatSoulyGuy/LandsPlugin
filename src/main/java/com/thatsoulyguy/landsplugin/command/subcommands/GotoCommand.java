package com.thatsoulyguy.landsplugin.command.subcommands;

import com.thatsoulyguy.landsplugin.command.LCommand;
import com.thatsoulyguy.landsplugin.command.LCommandRegistration;
import com.thatsoulyguy.landsplugin.world.LWorldManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GotoCommand extends LCommand
{

    @Override
    public int Execute(Player player, String[] args)
    {
        if(LWorldManager.PlayerAllowed(player, "land-" + args[0]))
            LWorldManager.TeleportPlayer(player, "land-" + args[0]);
        else
        {
            player.sendMessage(ChatColor.DARK_RED + "You do not have permission to access this world.");
            return -1;
        }

        return 0;
    }

    @Override
    public LCommandRegistration Register()
    {
        return LCommandRegistration.Register("goto", "Allows you to go to another player's world.", "/lands goto <land>");
    }
}