package me.kmaxi.parkourtimer.managers;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerManager {
    private final ParkourTimerMain plugin;

    private final Player player;
    private double currentParkourTime;
    private ParkourManager parkour;
    private Boolean justStartedParkour;

    public PlayerManager(Player player, ParkourTimerMain plugin) {
        this.player = player;
        this.currentParkourTime = 0;
        this.parkour = null;
        this.plugin = plugin;
        this.setJustStartedParkour(false);
    }

    public double getCurrentParkourTime() {
        return currentParkourTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCurrentParkourTime(double currentParkourTime) {
        this.currentParkourTime = currentParkourTime;
    }

    public void setParkour(ParkourManager parkour) {
        this.parkour = parkour;
    }

    public ParkourManager getParkour() {
        return parkour;
    }

    public void setJustStartedParkour(Boolean justStartedParkour) {
        this.justStartedParkour = justStartedParkour;
        if (justStartedParkour){
            new BukkitRunnable(){

                @Override
                public void run() {
                    setJustStartedParkour(false);
                }
            }.runTaskLater(plugin, 40);
        }
    }

    public Boolean getJustStartedParkour() {
        return justStartedParkour;
    }
}
