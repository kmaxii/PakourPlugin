package me.kmaxi.parkourtimer.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {


    public static ItemStack leaveParkour(){
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "LEAVE PARKOUR");
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public static ItemStack setCheckpointItem(){
        ItemStack itemStack = new ItemStack(Material.SEA_LANTERN);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "SET CHECKPOINT");
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public static ItemStack teleportToCheckpoint(){
        ItemStack itemStack = new ItemStack(Material.GLOWSTONE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "TELEPORT TO CHECKPOINT");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static void addParkourItems(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(8, Items.leaveParkour());
        player.getInventory().setItem(0, Items.setCheckpointItem());
    }

}
