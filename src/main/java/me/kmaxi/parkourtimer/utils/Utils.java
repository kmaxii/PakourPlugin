package me.kmaxi.parkourtimer.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void setEXP(double time, Player player){
        int xpLevel = (int) time;
        float f = (float) time - xpLevel;
        player.setExp(f);
        player.setLevel(xpLevel);
    }

    public static void resetEXP(Player player){
        player.setExp(0f);
        player.setLevel(0);
    }


}
