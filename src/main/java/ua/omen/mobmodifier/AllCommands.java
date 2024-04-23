package ua.omen.mobmodifier;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AllCommands implements CommandExecutor {
    private final MobModifier plugin;
    String hexPurpleColor = "#9900FF";

    public AllCommands(MobModifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("mobmodifier.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            plugin.reloadConfig();
            plugin.onDisable();
            plugin.onEnable();
            sender.sendMessage(ChatColor.GREEN + "MobModifier configuration and plugin reloaded.");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission("mobmodifier.help")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            sendHelpMessage(sender);
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("add")) {
            if (!sender.hasPermission("mobmodifier.add")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            if (args.length != 3) {
                sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /mobmodifier add <NameMob> <chance>");
                return true;
            }

            // Delegate the handling to AddCommand
            AddCommand addCommand = new AddCommand(plugin);
            return addCommand.onCommand(sender, command, label, args);
        }
        return false;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Mob" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "M" + ChatColor.GRAY + ChatColor.BOLD + "]" + ChatColor.AQUA + " Mob Modifier v1.0");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/mobmodifier help" + ChatColor.GRAY + " - Show the list of commands");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/mobmodifier reload" + ChatColor.GRAY + " - Reload the plugin configuration");
        sender.sendMessage(ChatColor.GRAY + "");
    }
}

