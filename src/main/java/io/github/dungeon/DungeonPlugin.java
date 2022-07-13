package io.github.dungeon;

import io.github.dungeon.cache.local.DungeonLocalCache;
import io.github.dungeon.commands.DungeonStartCommand;
import io.github.dungeon.config.DungeonConfig;
import io.github.dungeon.listeners.DungeonListeners;
import io.github.dungeon.misc.utils.SerializedLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class DungeonPlugin extends JavaPlugin {

    private static DungeonPlugin instance;

    private DungeonConfig dungeonConfig;
    private DungeonLocalCache dungeonLocalCache;

    public static DungeonPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        saveDefaultConfig();

        ConfigurationSection section = getConfig().getConfigurationSection("Location");
        if (section != null) {
            dungeonConfig = new DungeonConfig(
                    new SerializedLocation(
                            section.getString("worldName"),
                            section.getDouble("x"),
                            section.getDouble("y"),
                            section.getDouble("z"),
                            (float) section.getDouble("yaw"),
                            (float) section.getDouble("pitch")
                    )
            );
        }

        dungeonLocalCache = new DungeonLocalCache();

        getCommand("start").setExecutor(new DungeonStartCommand(this));
        getServer().getPluginManager().registerEvents(new DungeonListeners(this), this);

        getLogger().info("SingleDungeon plugin successfully started!");
    }

    public DungeonConfig getDungeonConfig() {
        return dungeonConfig;
    }

    public DungeonLocalCache getDungeonLocalCache() {
        return dungeonLocalCache;
    }
}
