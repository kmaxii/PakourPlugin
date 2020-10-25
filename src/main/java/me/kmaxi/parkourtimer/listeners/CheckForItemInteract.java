package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
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
        if ((event.getItem()).equals(Items.leaveParkour())){
            plugin.functions.teleportToStart(playerManager, playerManager.getParkour());
            event.getPlayer().getInventory().clear();
        }
        if ((event.getItem().equals(Items.setCheckpointItem()))){
            playerManager.setCheckPoint(player.getLocation());
            player.getInventory().remove(Items.setCheckpointItem());
            player.getInventory().setItem(1, Items.teleportToCheckpoint());
        }
        if ((event.getItem().equals(Items.teleportToCheckpoint()))){
            player.getInventory().remove(Items.teleportToCheckpoint());
            player.teleport(playerManager.getCheckPoint());
            playerManager.setCheckPoint(null);
        }

    }
}
