package me.kmaxi.parkourtimer.utils;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.configs.blocksconfig.TpLocation;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class Items {
    public final ParkourTimerMain plugin;

    public Items(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    public static void addParkourItems(Player player, ParkourTimerMain plugin) {
        player.getInventory().clear();
        player.getInventory().setItem(8, Items.getItem("leaveParkour", plugin));
        player.getInventory().setItem(0, getItem("setCheckpoint", plugin));
        player.getInventory().setItem(7, Items.getItem("hidePlayers", plugin));
    }

    public static void addLobbyItems(Player player, ParkourTimerMain plugin) {
        player.getInventory().clear();
        if (plugin.messegesConfig.getConfigBooleanData().get("useLobbyItems")) {
            for (ParkourManager parkourManager : plugin.parkours) {
                if (!plugin.blocksConfig.getTpLocations().keySet().contains(parkourManager.getName())) {
                    plugin.blocksConfig.addItem(parkourManager.getName(), (Location) plugin.getConfig().get(parkourManager.getName() + ".teleport"), "&6Teleport to &d" + parkourManager.getName() + "&6 parkour");
                }
            }
            for (TpLocation tpLocation : plugin.blocksConfig.tpLocations.values()) {
                if (tpLocation.getKey().equals("leaveParkour")
                        || tpLocation.getKey().equals("setCheckpoint")
                        || tpLocation.getKey().equals("teleportToCheckpoint")
                        || tpLocation.getKey().equals("hidePlayers")
                        || tpLocation.getKey().equals("showPlayers")) {
                    continue;
                }
                player.getInventory().setItem(tpLocation.getSlot(), tpLocation.getItem());
            }

        }
    }

    public static ItemStack getItem(String path, ParkourTimerMain plugin) {
        ItemStack itemStack = plugin.blocksConfig.getLocation(path).getItem();
        return itemStack;
    }



}
