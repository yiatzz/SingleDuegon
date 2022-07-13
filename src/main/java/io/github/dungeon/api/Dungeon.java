package io.github.dungeon.api;

import io.github.dungeon.DungeonPlugin;
import io.github.dungeon.entity.CustomZombie;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public record Dungeon(
        Player user
) {

    public static final List<PacketPlayOutSpawnEntity> SPAWN_PACKETS = new ArrayList<>();
    public static final List<PacketPlayOutEntityDestroy> DESTROY_PACKETS = new ArrayList<>();
    private static final int ZOMBIES_LIMIT = 10;
    private static boolean PREPARED = false;

    public void start() {
        user.sendMessage(ChatColor.translateAlternateColorCodes(
                '&',
                "&4&lWARNING! &cA zombie wave is coming in 30 seconds. Get ready!"
        ));

        Bukkit.getScheduler().runTaskLater(
                DungeonPlugin.getInstance(),
                () -> {

                    if (!user.isOnline()) {
                        return;
                    }

                    {
                        if (!PREPARED) {

                            WorldServer worldServer = ((CraftWorld) user.getLocation().getWorld()).getHandle();

                            for (int i = 0; i < ZOMBIES_LIMIT; i++) {
                                preparePackets(new CustomZombie(worldServer));
                            }

                            PREPARED = true;
                        }
                    }

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        user.hidePlayer(DungeonPlugin.getInstance(), onlinePlayer);
                    }

                    NetworkManager networkManager = ((CraftPlayer) user).getHandle().b.a;
                    for (PacketPlayOutSpawnEntity spawnPacket : SPAWN_PACKETS) {
                        networkManager.a(spawnPacket);
                    }

                    user.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            "&4&lWARNING! &cThe Zombie wave is here! Good luck."
                    ));

                }, 30 * 20L
        );

    }

    public void cancel() {
        if (!user.isOnline()) {
            return;
        }

        NetworkManager networkManager = ((CraftPlayer) user).getHandle().b.a;

        for (PacketPlayOutEntityDestroy destroyPacket : DESTROY_PACKETS) {
            networkManager.a(destroyPacket);
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            user.showPlayer(DungeonPlugin.getInstance(), onlinePlayer);
        }
    }

    private void preparePackets(CustomZombie customZombie) {
        DESTROY_PACKETS.add(
                new PacketPlayOutEntityDestroy(customZombie.getBukkitEntity().getEntityId())
        );

        SPAWN_PACKETS.add(
                new PacketPlayOutSpawnEntity(customZombie)
        );
    }
}
