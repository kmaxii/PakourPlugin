package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CancelMount implements Listener {
    private final ParkourTimerMain plugin;

    public CancelMount(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onClick(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if (plugin.players.get(player).getParkour() == null){
            return;
        }
        event.setCancelled(true);
    }

}
