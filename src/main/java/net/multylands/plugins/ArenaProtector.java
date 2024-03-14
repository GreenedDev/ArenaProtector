package net.multylands.plugins;

import net.multylands.plugins.commands.DisableProtectionCommand;
import net.multylands.plugins.commands.ReloadCommand;
import net.multylands.plugins.listeners.BlocksListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class ArenaProtector extends JavaPlugin {
    public static List<Block> blockList = new ArrayList<>();
    public static List<UUID> playersWhoHaveProtectionDisabled = new ArrayList<>();

    public static HashMap<Location, Location> excludedAreas = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlocksListener(this), this);
        getCommand("disablearenaprotection").setExecutor(new DisableProtectionCommand(this));
        getCommand("reloadarenaprotection").setExecutor(new ReloadCommand(this));
        saveDefaultConfig();
        for (String keyNumber : getConfig().getConfigurationSection("ExcludedLocations").getKeys(false)) {
            Location pos1 = getConfig().getLocation("ExcludedLocations."+keyNumber+".pos1");
            Location pos2 = getConfig().getLocation("ExcludedLocations."+keyNumber+".pos2");
            excludedAreas.put(pos1, pos2);
        }
    }
    public void customReloadConfig() {
        reloadConfig();
        excludedAreas.clear();
        for (String keyNumber : getConfig().getConfigurationSection("ExcludedLocations").getKeys(false)) {
            Location pos1 = getConfig().getLocation("ExcludedLocations."+keyNumber+".pos1");
            Location pos2 = getConfig().getLocation("ExcludedLocations."+keyNumber+".pos2");
            excludedAreas.put(pos1, pos2);
        }
    }
    @Override
    public void onDisable() {
        for (Block block : blockList) {
            block.breakNaturally();
        }
    }
}
