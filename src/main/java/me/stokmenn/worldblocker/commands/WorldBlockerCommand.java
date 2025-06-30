package me.stokmenn.worldblocker.commands;

import me.stokmenn.worldblocker.WorldBlocker;
import me.stokmenn.worldblocker.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WorldBlockerCommand implements CommandExecutor {
    private final WorldBlocker plugin;

    public WorldBlockerCommand(WorldBlocker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("worldblocker.reload")) {
            sender.sendMessage(Config.noPermissionToReload);
            return false;
        }
        boolean oldBlockAllEntities = Config.blockAllEntities;
        boolean oldBlockPlayers = Config.blockPlayers;

        Bukkit.getAsyncScheduler().runNow(plugin, task -> {
            Config.reload();

            if (Config.blockAllEntities != oldBlockAllEntities) {
                if (Config.blockAllEntities) {
                    Bukkit.getPluginManager().registerEvents(plugin.getEntityPortalListener(), plugin);
                } else {
                    HandlerList.unregisterAll(plugin.getEntityPortalListener());
                }
            }
            if (Config.blockPlayers != oldBlockPlayers) {
                if (Config.blockPlayers) {
                    Bukkit.getPluginManager().registerEvents(plugin.getPlayerChangedWorldListener(), plugin);
                } else {
                    HandlerList.unregisterAll(plugin.getPlayerChangedWorldListener());
                }
            }
        });

        sender.sendMessage(Config.configReloaded);
        return true;
    }
}