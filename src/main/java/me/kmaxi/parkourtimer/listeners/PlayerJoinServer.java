package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinServer implements Listener {
    private final ParkourTimerMain plugin;

    public PlayerJoinServer(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        if (!plugin.players.containsKey(event.getPlayer())){
            plugin.players.put(event.getPlayer(), new PlayerManager(event.getPlayer(), plugin));
        }
    }
}
