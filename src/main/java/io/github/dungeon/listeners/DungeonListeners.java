package io.github.dungeon.listeners;

import io.github.dungeon.DungeonPlugin;
import io.github.dungeon.api.Dungeon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DungeonListeners implements Listener {

    private final DungeonPlugin plugin;

    public DungeonListeners(DungeonPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(PlayerQuitEvent event) {
        Dungeon dungeon = plugin.getDungeonLocalCache().remove(event.getPlayer().getUniqueId());
        if (dungeon != null) {
            dungeon.cancel();
        }
    }

}
