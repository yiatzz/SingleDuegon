package io.github.dungeon.misc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public record SerializedLocation(
        String worldName,
        double x,
        double y,
        double z,
        float yaw,
        float pitch
) {

    public Location parse() {
        return new Location(
                Bukkit.getWorld(this.worldName),
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch
        );
    }

}
