package io.github.dungeon.commands;

import io.github.dungeon.DungeonPlugin;
import io.github.dungeon.api.Dungeon;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DungeonStartCommand implements CommandExecutor {

    private final DungeonPlugin plugin;

    public DungeonStartCommand(DungeonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be execute by a player.");
            return false;
        }

        if (plugin.getDungeonLocalCache().get(player.getUniqueId()) != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "You are already in a dungeon!"));
            return false;
        }

        player.teleport(plugin.getDungeonConfig().location().parse());

        player.playSound(player.getLocation(), Sound.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.5F, 1.0F);
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 0.5F, 1.0F);

        Dungeon dungeon = new Dungeon(player);

        plugin.getDungeonLocalCache().put(player.getUniqueId(), dungeon);

        dungeon.start();

        return true;
    }
}
