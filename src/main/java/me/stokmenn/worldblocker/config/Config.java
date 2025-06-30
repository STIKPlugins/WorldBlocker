package me.stokmenn.worldblocker.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class Config {
    private static JavaPlugin plugin;

    public static boolean blockAllEntities;

    public static boolean blockPlayers;

    public static boolean ignoreImmunePermission;
    public static String immunePermission;

    public static boolean doSendMessage;
    public static Component canNotEnterWorld;
    public static Component noPermissionToReload;
    public static Component configReloaded;

    public static final Set<World> blockedWorlds = new HashSet<>();

    public static void init(JavaPlugin plugin) {
        Config.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public static void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        blockAllEntities = config.getBoolean("blockAllEntities", false);

        blockPlayers = config.getBoolean("blockPlayers", true);

        ignoreImmunePermission = config.getBoolean("ignoreImmunePermission", false);

        immunePermission = config.getString("immunePermission", "worldblocker.immune");

        doSendMessage = config.getBoolean("doSendMessage", true);

        canNotEnterWorld = MiniMessage.miniMessage().deserialize(config.getString(
                "canNotEnterWorld", "<red>✘ <white>You can't enter this world!"));

        noPermissionToReload = MiniMessage.miniMessage().deserialize(config.getString(
                "noPermissionToReload", "<red>✘ <white>You don't have permission to reload Config!"));

        configReloaded = MiniMessage.miniMessage().deserialize(config.getString(
                "configReloaded", "<green>✔ <white>Config reloaded!"));

        blockedWorlds.clear();
        for (String worldName : config.getStringList("blockedWorlds")) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                blockedWorlds.add(world);
            } else {
                plugin.getLogger().warning("Unknown world in config: " + worldName);
            }
        }
    }
}