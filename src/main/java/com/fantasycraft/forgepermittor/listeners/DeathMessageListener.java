package com.fantasycraft.forgepermittor.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by thomas on 8/22/2014.
 */
public class DeathMessageListener extends DisableableEvent implements Listener {
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event)
    {
        if (!isEnabled())
            return;
        if (event.getDeathMessage().equalsIgnoreCase("death.weapon")) {
            event.setDeathMessage(event.getEntity().getName() + " was killed" + (event.getEntity().getKiller() != null ? " by " + event.getEntity().getKiller().getName() : ""));
        }
    }
}
