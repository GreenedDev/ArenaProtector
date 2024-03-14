package net.multylands.plugins;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utils {
    public static String Color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

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
}
