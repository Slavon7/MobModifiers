package ua.omen.mobmodifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddCommand implements CommandExecutor {
    private final MobModifier plugin;

    public AddCommand(MobModifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        if (!sender.hasPermission("mobmodifier.add")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /mobmodifier add <NameMob> <chance>");
            return true;
        }

        String mobName = args[1].toUpperCase();
        int chance;

        try {
            chance = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid chance value. Please enter a valid integer.");
            return true;
        }

        EntityType entityType = EntityType.fromName(mobName);
        if (entityType == null) {
            sender.sendMessage(ChatColor.RED + "Invalid mob name. Please enter a valid mob name.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType() == Material.AIR) {
            sender.sendMessage(ChatColor.RED + "You must hold an item in your hand to add it to the mob's drops.");
            return true;
        }

        // Add the item with the specified chance to the mob's drops in the config
        String itemName = itemInHand.getType().toString();
        plugin.getConfig().set("Mobs." + mobName + ".Items." + itemName, chance);
        plugin.saveConfig();

        sender.sendMessage(ChatColor.GREEN + "Item '" + itemName + "' with chance " + chance + " has been added to the drops for the mob '" + mobName + "'.");

        return true;
    }
}
