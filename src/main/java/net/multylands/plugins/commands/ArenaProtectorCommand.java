package net.multylands.plugins.commands;

import net.multylands.plugins.ArenaProtector;
import net.multylands.plugins.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArenaProtectorCommand implements CommandExecutor, TabCompleter {
    ArenaProtector plugin;

    public ArenaProtectorCommand(ArenaProtector plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            for (String message : plugin.getConfig().getStringList("help")) {
                sender.sendMessage(ChatUtils.Color(message));
            }
            return false;
        }
        CommandExecutor executor = ArenaProtector.commandExecutors.get(args[0]);
        if (executor == null) {
            sender.sendMessage(ChatUtils.Color(plugin.getConfig().getString("command-usage").replace("%label%", label)));
            return false;
        }
        executor.onCommand(sender, command, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabCompleteStrings = new ArrayList<>();
        String reloadCommand = "reload";
        String bypassCommand = "bypass";
        if (reloadCommand.startsWith(args[0])) {
            tabCompleteStrings.add(reloadCommand);
        }
        if (bypassCommand.startsWith(args[0])) {
            tabCompleteStrings.add(bypassCommand);
        }
        return tabCompleteStrings;
    }
}
