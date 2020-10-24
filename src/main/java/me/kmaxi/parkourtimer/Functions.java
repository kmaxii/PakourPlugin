package me.kmaxi.parkourtimer;

import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
import org.bukkit.entity.Player;

public class Functions {
    private final ParkourTimerMain plugin;

    public Functions(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    public void teleportToStart(PlayerManager playerManager, ParkourManager parkour){
        Player player = playerManager.getPlayer();
        player.teleport(parkour.getTeleport());
        playerManager.setParkour(null);
    }

}
