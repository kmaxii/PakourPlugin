package me.kmaxi.parkourtimer.utils;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
    public final ParkourTimerMain plugin;

    public Items(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    public static void addParkourItems(Player player, ParkourTimerMain plugin){
        player.getInventory().clear();
        player.getInventory().setItem(8, Items.getItem("leaveParkour", plugin));
        player.getInventory().setItem(0, getItem("setCheckpoint", plugin));
        player.getInventory().setItem(7, Items.getItem("hidePlayers", plugin));
    }



    public static ItemStack getItem(String path, ParkourTimerMain plugin){
        String pathToMaterial = "blocks." + path + ".material";
        String pathToText = "blocks." + path + ".text";
        ItemStack itemStack = new ItemStack(Material.valueOf(plugin.messegesConfig.getMessagesConfig().getString(pathToMaterial)));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Utils.color(plugin.messegesConfig.getMessagesConfig().getString(pathToText)));
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
