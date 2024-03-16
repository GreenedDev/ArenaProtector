package net.multylands.plugins.listeners;

import net.multylands.plugins.ArenaProtector;
import net.multylands.plugins.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlocksListener implements Listener {
    ArenaProtector plugin;

    public BlocksListener(ArenaProtector plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (ArenaProtector.playersWhoBypassEnabled.contains(player.getUniqueId())) {
            return;
        }
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        //0 - blacklist mode
        //1 - whitelist mode
        //if chosen blacklist 0, only the areas entered below will not be protected.
        //if chosen whitelist 1, only the areas that are entered below will be protected.
        int mode = plugin.getConfig().getInt("mode");
        if (mode == 0) {
            for (Location pos1 : ArenaProtector.excludedAreas.keySet()) {
                Location pos2 = ArenaProtector.excludedAreas.get(pos1);
                if (Utils.checkIfIsInBetweenLocations(pos1, pos2, blockLocation)) {
                    return;
                }
            }
        } else {
            boolean doesItContainTheBlock = false;
            for (Location pos1 : ArenaProtector.excludedAreas.keySet()) {
                Location pos2 = ArenaProtector.excludedAreas.get(pos1);
                if (Utils.checkIfIsInBetweenLocations(pos1, pos2, blockLocation)) {
                    doesItContainTheBlock = true;
                }
            }
            if (!doesItContainTheBlock) {
                return;
            }
        }
        ArenaProtector.blockList.add(block);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ArenaProtector.blockList.remove(block);
            block.breakNaturally();
        }, 20 * 60 * 30);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (ArenaProtector.playersWhoBypassEnabled.contains(player.getUniqueId())) {
            return;
        }
        Block block = event.getBlock();
        if (ArenaProtector.blockList.contains(block)) {
            return;
        }
        Location blockLocation = block.getLocation();
        //0 - blacklist mode
        //1 - whitelist mode
        //if chosen blacklist 0, only the areas entered below will not be protected.
        //if chosen whitelist 1, only the areas that are entered below will be protected.
        int mode = plugin.getConfig().getInt("mode");
        if (mode == 0) {
            for (Location pos1 : ArenaProtector.excludedAreas.keySet()) {
                Location pos2 = ArenaProtector.excludedAreas.get(pos1);
                if (Utils.checkIfIsInBetweenLocations(pos1, pos2, blockLocation)) {
                    return;
                }
            }
        } else {
            boolean doesItContainTheBlock = false;
            for (Location pos1 : ArenaProtector.excludedAreas.keySet()) {
                Location pos2 = ArenaProtector.excludedAreas.get(pos1);
                if (Utils.checkIfIsInBetweenLocations(pos1, pos2, blockLocation)) {
                    doesItContainTheBlock = true;
                    break;
                }
            }
            if (!doesItContainTheBlock) {
                return;
            }
        }
        player.sendMessage(Utils.Color(plugin.getConfig().getString("break-deny")));
        event.setCancelled(true);
    }
}
