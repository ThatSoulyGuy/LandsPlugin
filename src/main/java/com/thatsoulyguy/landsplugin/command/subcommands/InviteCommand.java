package com.thatsoulyguy.landsplugin.command.subcommands;

import com.thatsoulyguy.landsplugin.command.LCommand;
import com.thatsoulyguy.landsplugin.command.LCommandRegistration;
import com.thatsoulyguy.landsplugin.world.LWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand extends LCommand
{

    @Override
    public int Execute(Player player, String[] args)
    {
        Player target = Bukkit.getPlayer(args[0]);

        LWorldManager.InvitePlayer(target, "land-" + player.getName());

        return 0;
    }

    @Override
    public LCommandRegistration Register()
    {
        return LCommandRegistration.Register("invite", "Invites a player to your world", "/land invite <player>");
    }
}