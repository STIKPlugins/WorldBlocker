package me.stokmenn.worldblocker.listeners;

import me.stokmenn.worldblocker.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL
                && event.getCause() != PlayerTeleportEvent.TeleportCause.END_PORTAL) return;
        if (!Config.ignoreImmunePermission && player.hasPermission(Config.immunePermission)) return;
        if (!Config.blockedWorlds.contains(event.getTo().getWorld())) return;

        event.setCancelled(true);

        if (Config.doSendMessage) {
            player.sendMessage(Config.canNotEnterWorld);
        }
    }
}
