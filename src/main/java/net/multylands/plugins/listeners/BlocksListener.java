package net.multylands.plugins.listeners;

import net.multylands.plugins.ArenaProtector;
import net.multylands.plugins.utils.ArenaUtils;
import net.multylands.plugins.utils.ChatUtils;
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
        int mode = plugin.getConfig().getInt("mode");
        if (ArenaUtils.IsInWhitelistOrNotInBlacklist(mode, blockLocation)) {
            return;
        }
        ArenaProtector.blockList.add(block);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ArenaProtector.blockList.remove(block);
            block.breakNaturally();
        }, 20L * 60 * plugin.getConfig().getInt("despawn-delay"));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (ArenaProtector.playersWhoBypassEnabled.contains(player.getUniqueId())) {
            return;
        }
        Block block = event.getBlock();
        if (ArenaUtils.isMaterialWhitelisted(plugin, block)) {
            return;
        }
        if (ArenaProtector.blockList.contains(block)) {
            return;
        }
        Location blockLocation = block.getLocation();
        int mode = plugin.getConfig().getInt("mode");
        if (ArenaUtils.IsInWhitelistOrNotInBlacklist(mode, blockLocation)) {
            return;
        }
        player.sendMessage(ChatUtils.Color(plugin.getConfig().getString("break-deny")));
        ArenaProtector.blockList.remove(block);
        event.setCancelled(true);
    }
}
