package me.kmaxi.parkourtimer.managers;

import org.bukkit.Location;

public class ParkourManager {
    private final Location start;
    private final Location end;
    private final Location teleport;
    private double y;
    private String name;

    public ParkourManager(Location start, Location end, Location teleport, Double y, String name) {
        this.start = start;
        this.end = end;
        this.y = y;
        this.name = name;
        this.teleport = teleport;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public Location getTeleport() {
        return teleport;
    }
}
