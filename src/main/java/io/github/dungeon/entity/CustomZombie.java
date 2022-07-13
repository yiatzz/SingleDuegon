package io.github.dungeon.entity;

import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.level.World;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;

public class CustomZombie extends EntityZombie {

    public CustomZombie(World world) {
        super(world);

        setItemSlot(
                EnumItemSlot.a,
                CraftItemStack.asNMSCopy(
                        new org.bukkit.inventory.ItemStack(Material.DIAMOND_SWORD)
                ),
                false
        );
    }

    @Override
    public boolean d_() {
        return super.d_();
    }

    /*
    incompatible types
     */
    @Override
    public boolean x() {
        return false;
    }
}
