package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinServer implements Listener {
    private final ParkourTimerMain plugin;

    public PlayerJoinServer(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.players.containsKey(player)) {
            plugin.players.put(player, new PlayerManager(event.getPlayer(), plugin));
        }
        Items.addLobbyItems(player, plugin);
    }
}
