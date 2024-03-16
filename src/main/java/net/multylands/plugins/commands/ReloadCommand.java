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
        if (args.length != 1) {
            sender.sendMessage(Utils.Color(plugin.getConfig().getString("command-usage").replace("%label%", label)));
            return false;
        }
        if (!sender.hasPermission("arenaprotector.reload")) {
            sender.sendMessage(Utils.Color(plugin.getConfig().getString("no-perm")));
            return false;
        }
        plugin.customReloadConfig();
        sender.sendMessage(Utils.Color(plugin.getConfig().getString("reload-success")));
        return false;
    }
}
