package ua.omen.mobmodifier;

import org.bukkit.inventory.ItemStack;

public class ItemChancePair {
    private ItemStack itemStack;
    private int chance;

    public ItemChancePair(ItemStack itemStack, int chance) {
        this.itemStack = itemStack;
        this.chance = chance;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getChance() {
        return chance;
    }
}
