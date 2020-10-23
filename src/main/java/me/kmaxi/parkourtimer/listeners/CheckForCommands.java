package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CheckForCommands implements Listener {
    private final ParkourTimerMain plugin;

    public CheckForCommands(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    public void sendCommand(PlayerCommandPreprocessEvent event){
        if (!(plugin.players.get(event.getPlayer()).getParkour() == null)){
            event.setCancelled(true);
        }
    }
}
