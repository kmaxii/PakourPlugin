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
        if (!(event.hasItem())){
            return;
        }
        if ((event.getItem()).equals(Items.leaveParkour())){
            PlayerManager playerManager = plugin.players.get(player);
            plugin.functions.teleportToStart(playerManager, playerManager.getParkour());
            event.getPlayer().getInventory().clear();
        }
        if ((event.getItem().equals(Items.setCheckpointItem()))){
            plugin.players.get(player).setCheckPoint(player.getLocation());
            player.getInventory().remove(Items.setCheckpointItem());
        }
    }
}
