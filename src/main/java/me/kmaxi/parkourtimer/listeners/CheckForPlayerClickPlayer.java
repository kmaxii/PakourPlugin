package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class CheckForPlayerClickPlayer implements Listener {
    public final ParkourTimerMain plugin;

    public CheckForPlayerClickPlayer(ParkourTimerMain plugin){
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onClickPlayer(PlayerInteractAtEntityEvent event){
        if (plugin.players.get(event.getPlayer()).getParkour() != null){
            event.setCancelled(true);
        }
    }
}
