package me.kmaxi.parkourtimer.utils;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;

public class Utils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void setEXP(double time, Player player) {
        int xpLevel = (int) time;
        float f = (float) time - xpLevel;
        player.setExp(f);
        player.setLevel(xpLevel);
    }

    public static void resetEXP(Player player) {
        player.setExp(0f);
        player.setLevel(0);
    }

    public static void hidePlayers(Player player, ParkourTimerMain plugin) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1 == player) {
                continue;
            }
            player.hidePlayer(plugin, player1);
        }
    }

    public static void showPlayers(Player player, ParkourTimerMain plugin) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1 == player) {
                continue;
            }
            player.showPlayer(plugin, player1);
        }
    }

    public static Material getRandomMaterial() {
        Material[] materials = Material.values();
        Random random = new Random();
        int i = random.nextInt(materials.length - 1);
        return materials[i];
    }


}
