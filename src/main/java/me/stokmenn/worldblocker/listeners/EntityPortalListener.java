package me.stokmenn.worldblocker.listeners;

import me.stokmenn.worldblocker.config.Config;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;

public class EntityPortalListener implements Listener {

    @EventHandler
    public void onEntityChangedWorld(EntityTeleportEvent event) {
        Location to = event.getTo();
        if (to == null) return;
        if (to.getWorld() == event.getFrom().getWorld()) return;
        if (!Config.blockedWorlds.contains(to.getWorld())) return;

        event.getEntity().teleportAsync(event.getFrom());
    }
}
