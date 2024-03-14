package net.multylands.plugins.commands;

import net.multylands.plugins.ArenaProtector;
import net.multylands.plugins.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DisableProtectionCommand implements CommandExecutor {
    ArenaProtector plugin;
    public DisableProtectionCommand(ArenaProtector plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Utils.Color("&cExecutor must be player!"));
            return false;
        }
        if (args.length != 0) {
            player.sendMessage(Utils.Color("&cUsage: /"+label));
            return false;
        }
        if (!player.hasPermission("arenaprotector.disableforhimself")) {
            player.sendMessage(Utils.Color("&cYou don't have permission to execute this command!"));
            return false;
        }
        if (ArenaProtector.playersWhoHaveProtectionDisabled.contains(player.getUniqueId())) {
            ArenaProtector.playersWhoHaveProtectionDisabled.remove(player.getUniqueId());
            player.sendMessage(Utils.Color("&aProtection enabled for &b"+player.getName()));
            return false;
        }
        ArenaProtector.playersWhoHaveProtectionDisabled.add(player.getUniqueId());
        player.sendMessage(Utils.Color("&cProtection disabled for &b"+player.getName()));
        return false;
    }
}
