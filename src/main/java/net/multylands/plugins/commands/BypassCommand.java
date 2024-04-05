package net.multylands.plugins.commands;

import net.multylands.plugins.ArenaProtector;
import net.multylands.plugins.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BypassCommand implements CommandExecutor {
    ArenaProtector plugin;

    public BypassCommand(ArenaProtector plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatUtils.Color(plugin.getConfig().getString("only-player")));
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(ChatUtils.Color(plugin.getConfig().getString("command-usage").replace("%label%", label)));
            return false;
        }
        if (!player.hasPermission("arenaprotector.bypass")) {
            player.sendMessage(ChatUtils.Color(plugin.getConfig().getString("no-perm")));
            return false;
        }
        if (ArenaProtector.playersWhoBypassEnabled.contains(player.getUniqueId())) {
            ArenaProtector.playersWhoBypassEnabled.remove(player.getUniqueId());
            player.sendMessage(ChatUtils.Color(plugin.getConfig().getString("admin-bypass-off").replace("%player%", player.getName())));
            return false;
        }
        ArenaProtector.playersWhoBypassEnabled.add(player.getUniqueId());
        player.sendMessage(ChatUtils.Color(plugin.getConfig().getString("admin-bypass-on").replace("%player%", player.getName())));
        return false;
    }
}
