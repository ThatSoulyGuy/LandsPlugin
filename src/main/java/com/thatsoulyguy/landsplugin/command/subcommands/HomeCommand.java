package com.thatsoulyguy.landsplugin.command.subcommands;

import com.thatsoulyguy.landsplugin.command.LCommand;
import com.thatsoulyguy.landsplugin.command.LCommandRegistration;
import com.thatsoulyguy.landsplugin.world.LWorldManager;
import org.bukkit.entity.Player;

public class HomeCommand extends LCommand
{

    @Override
    public int Execute(Player player, String[] args)
    {
        LWorldManager.TeleportPlayer(player, "land-" + player.getName());

        return 0;
    }

    @Override
    public LCommandRegistration Register()
    {
        return LCommandRegistration.Register("home", "Teleports your 'home' land.", "/land home");
    }
}