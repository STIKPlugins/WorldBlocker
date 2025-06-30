package me.stokmenn.worldblocker;

import me.stokmenn.worldblocker.commands.WorldBlockerCommand;
import me.stokmenn.worldblocker.config.Config;
import me.stokmenn.worldblocker.listeners.EntityPortalListener;
import me.stokmenn.worldblocker.listeners.PlayerChangedWorldListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldBlocker extends JavaPlugin {
    private EntityPortalListener entityPortalListener;
    private PlayerChangedWorldListener playerChangedWorldListener;

    @Override
    public void onEnable() {
        entityPortalListener = new EntityPortalListener();
        playerChangedWorldListener = new PlayerChangedWorldListener();

        Config.init(this);

        if (Config.blockAllEntities) {
            Bukkit.getPluginManager().registerEvents(entityPortalListener, this);
        } else {
            HandlerList.unregisterAll(entityPortalListener);
        }
        if (Config.blockPlayers) {
            Bukkit.getPluginManager().registerEvents(playerChangedWorldListener, this);
        } else {
            HandlerList.unregisterAll(playerChangedWorldListener);
        }

        getCommand("worldblocker").setExecutor(new WorldBlockerCommand(this));
    }

    public EntityPortalListener getEntityPortalListener() {
        return entityPortalListener;
    }

    public PlayerChangedWorldListener getPlayerChangedWorldListener() {
        return playerChangedWorldListener;
    }
}