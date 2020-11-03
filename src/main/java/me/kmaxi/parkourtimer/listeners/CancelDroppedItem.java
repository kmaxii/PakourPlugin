package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CancelDroppedItem implements Listener {
    private final ParkourTimerMain plugin;

    public CancelDroppedItem(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

}
