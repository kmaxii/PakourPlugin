package me.kmaxi.parkourtimer.managers;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerManager {
    private final ParkourTimerMain plugin;

    private final Player player;
    private double currentParkourTime;
    private ParkourManager parkour;
    private Location checkpoint;

    public PlayerManager(Player player, ParkourTimerMain plugin) {
        this.player = player;
        this.currentParkourTime = 0;
        this.parkour = null;
        this.plugin = plugin;
        this.checkpoint = null;
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

    public Location getCheckPoint() {
        return checkpoint;
    }

    public void setCheckPoint(Location checkPoint) {
        this.checkpoint = checkPoint;
    }

    public void finishParkour(double time) {
        this.getParkour().getRecordClass().completedParkour(player, time);
    }
}
