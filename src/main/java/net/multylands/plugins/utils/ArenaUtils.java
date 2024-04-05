package net.multylands.plugins.utils;

import net.multylands.plugins.ArenaProtector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ArenaUtils {
    public static boolean checkIfIsInBetweenLocations(Location loc1, Location loc2, Location blockLoc) {
        double X1 = loc1.getX();
        double Z1 = loc1.getZ();
        double Y1 = loc1.getY();

        double X2 = loc2.getX();
        double Z2 = loc2.getZ();
        double Y2 = loc2.getY();

        double upperX = Math.max(X1, X2);
        double upperZ = Math.max(Z1, Z2);
        double upperY = Math.max(Y1, Y2);

        double lowerX = Math.min(X1, X2);
        double lowerZ = Math.min(Z1, Z2);
        double lowerY = Math.min(Y1, Y2);

        double pX = blockLoc.getX();
        double pZ = blockLoc.getZ();
        double pY = blockLoc.getY();


        return (pX <= upperX && pX >= lowerX && pZ <= upperZ && pZ >= lowerZ && pY <= upperY && pY >= lowerY);
    }

    public static boolean IsInWhitelistOrNotInBlacklist(int mode, Location blockLocation) {
        //0 - blacklist mode
        //1 - whitelist mode
        //if chosen blacklist 0, only the areas entered below will not be protected.
        //if chosen whitelist 1, only the areas that are entered below will be protected.
        if (mode == 0) {
            for (Location pos1 : ArenaProtector.excludedAreas.keySet()) {
                Location pos2 = ArenaProtector.excludedAreas.get(pos1);
                if (checkIfIsInBetweenLocations(pos1, pos2, blockLocation)) {
                    return false;
                }
            }
        } else {
            boolean doesItContainTheBlock = false;
            for (Location pos1 : ArenaProtector.excludedAreas.keySet()) {
                Location pos2 = ArenaProtector.excludedAreas.get(pos1);
                if (checkIfIsInBetweenLocations(pos1, pos2, blockLocation)) {
                    doesItContainTheBlock = true;
                }
            }
            if (!doesItContainTheBlock) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaterialWhitelisted(ArenaProtector plugin, Block block) {
        for (String materialName : plugin.getConfig().getStringList("whitelist-blocks")) {
            if (Material.getMaterial(materialName) != block.getType()) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static void loadAreas(ArenaProtector plugin) {
        for (String keyNumber : plugin.getConfig().getConfigurationSection("ExcludedLocations").getKeys(false)) {
            int x1 = plugin.getConfig().getInt("ExcludedLocations." + keyNumber + ".pos1.x");
            int y1 = plugin.getConfig().getInt("ExcludedLocations." + keyNumber + ".pos1.y");
            int z1 = plugin.getConfig().getInt("ExcludedLocations." + keyNumber + ".pos1.z");
            String world1 = plugin.getConfig().getString("ExcludedLocations." + keyNumber + ".pos1.x");

            int x2 = plugin.getConfig().getInt("ExcludedLocations." + keyNumber + ".pos2.x");
            int y2 = plugin.getConfig().getInt("ExcludedLocations." + keyNumber + ".pos2.y");
            int z2 = plugin.getConfig().getInt("ExcludedLocations." + keyNumber + ".pos2.z");
            String world2 = plugin.getConfig().getString("ExcludedLocations." + keyNumber + ".pos2.x");

            Location pos1 = new Location(Bukkit.getWorld(world1), x1, y1, z1);
            Location pos2 = new Location(Bukkit.getWorld(world2), x2, y2, z2);
            ArenaProtector.excludedAreas.put(pos1, pos2);
        }
    }
}
