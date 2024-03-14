package net.multylands.plugins.commands;

import net.multylands.plugins.ArenaProtector;
import net.multylands.plugins.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    ArenaProtector plugin;
    public ReloadCommand(ArenaProtector plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0) {
            sender.sendMessage(Utils.Color("&cUsage: /"+label));
            return false;
        }
        if (!sender.hasPermission("arenaprotector.reload")) {
            sender.sendMessage(Utils.Color("&cYou don't have permission to execute this command!"));
            return false;
        }
        plugin.customReloadConfig();
        sender.sendMessage(Utils.Color("&aConfig has been reloaded successfully!"));
        return false;
    }
}
