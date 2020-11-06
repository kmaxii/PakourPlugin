package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.configs.blocksconfig.TpLocation;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
import me.kmaxi.parkourtimer.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class CheckForItemInteract implements Listener {
    private final ParkourTimerMain plugin;

    public CheckForItemInteract(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerManager playerManager = plugin.players.get(player);
        if (!(event.hasItem())){
            return;
        }
        if (event.getItem().equals(Items.getItem("leaveParkour", plugin))){
            plugin.functions.teleportToStart(playerManager, playerManager.getParkour());
            event.getPlayer().getInventory().clear();
            return;
        }
        if (event.getItem().equals(Items.getItem("setCheckpoint", plugin))){
            playerManager.setCheckPoint(player.getLocation());
            player.getInventory().remove(Items.getItem("setCheckpoint", plugin));
            player.getInventory().setItem(1, Items.getItem("teleportToCheckpoint", plugin));
            return;
        }
        if (event.getItem().equals(Items.getItem("teleportToCheckpoint", plugin))){
            player.getInventory().remove(Items.getItem("teleportToCheckpoint", plugin));
            player.teleport(playerManager.getCheckPoint());
            playerManager.setCheckPoint(null);
            return;
        }
        if (event.getItem().equals(Items.getItem("hidePlayers", plugin))){
            player.getInventory().remove(Items.getItem("hidePlayers", plugin));
            Utils.hidePlayers(player, plugin);
            player.getInventory().setItem(6, Items.getItem("showPlayers", plugin));
            return;
        }
        if (event.getItem().equals(Items.getItem("showPlayers", plugin))){
            player.getInventory().remove(Items.getItem("showPlayers", plugin));
            Utils.showPlayers(player, plugin);
            player.getInventory().setItem(7, Items.getItem("hidePlayers", plugin));
            return;
        }
        for (ParkourManager parkourManager : plugin.parkours) {
            if (event.getItem().equals(Items.getItem(parkourManager.getName(), plugin))) {
                player.teleport(parkourManager.getTeleport());
                return;
            }
        }
        for (TpLocation tpLocation : plugin.blocksConfig.tpLocations.values()) {
            if (event.getItem().equals(tpLocation.getItem())){
                player.teleport(tpLocation.getLocation());
                return;
            }
        }

    }

}
