package net.multylands.plugins;

import net.multylands.plugins.commands.ArenaProtectorCommand;
import net.multylands.plugins.commands.BypassCommand;
import net.multylands.plugins.commands.ReloadCommand;
import net.multylands.plugins.listeners.BlocksListener;
import net.multylands.plugins.utils.ArenaUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class ArenaProtector extends JavaPlugin {
    public static List<Block> blockList = new ArrayList<>();
    public static List<UUID> playersWhoBypassEnabled = new ArrayList<>();

    public static HashMap<Location, Location> excludedAreas = new HashMap<>();
    public static HashMap<String, CommandExecutor> commandExecutors = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlocksListener(this), this);
        getCommand("arenaprotector").setExecutor(new ArenaProtectorCommand(this));
        commandExecutors.put("bypass", new BypassCommand(this));
        commandExecutors.put("reload", new ReloadCommand(this));
        saveDefaultConfig();
        ArenaUtils.loadAreas(this);

    }

    public void customReloadConfig() {
        reloadConfig();
        excludedAreas.clear();
        ArenaUtils.loadAreas(this);
    }

    @Override
    public void onDisable() {
        for (Block block : blockList) {
            block.breakNaturally();
        }
    }
}
