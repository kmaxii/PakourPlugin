package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class CancelPvp implements Listener {
    private final ParkourTimerMain plugin;

    public CancelPvp(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit (EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player) event.getEntity();
        if (plugin.players.get(player).getParkour() != null){
            event.setCancelled(true);
        }


    }

}
