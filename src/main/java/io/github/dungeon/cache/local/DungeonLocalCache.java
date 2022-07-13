package io.github.dungeon.cache.local;

import io.github.dungeon.api.Dungeon;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DungeonLocalCache {

    private final Map<UUID, Dungeon> dungeonsMap = new HashMap<>();

    public void put(UUID uuid, Dungeon dungeon) {
        dungeonsMap.put(uuid, dungeon);
    }

    public Dungeon get(UUID uuid) {
        return dungeonsMap.get(uuid);
    }

    public Dungeon remove(UUID uuid) {
        return dungeonsMap.remove(uuid);
    }

}
