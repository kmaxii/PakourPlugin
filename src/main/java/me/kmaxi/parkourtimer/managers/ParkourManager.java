package me.kmaxi.parkourtimer.managers;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.records.RecordTime;
import me.kmaxi.parkourtimer.records.Records;
import me.kmaxi.parkourtimer.records.RecordsHologram;
import org.bukkit.Location;

import java.util.ArrayList;


public class ParkourManager {
    private final ParkourTimerMain plugin;
    private final Location start;
    private final Location end;
    private final Location teleport;
    private final double y;
    private final String name;
    private final Records records;
    private final Location leaderboard;
    private final RecordsHologram recordsHologram;

    public ParkourManager(Location start, Location end, Location teleport, Double y, String name, ParkourTimerMain plugin, Location leaderboard) {
        this.start = start;
        this.end = end;
        this.y = y;
        this.name = name;
        this.teleport = teleport;
        this.records = new Records(plugin, this);
        this.plugin = plugin;
        this.leaderboard = leaderboard;
        this.recordsHologram = new RecordsHologram(plugin, this);
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

    public ArrayList<RecordTime> getRecords() {
        return records.getRecords();
    }

    public Records getRecordClass() {
        return records;
    }

    public Location getTeleport() {
        return teleport;
    }

    public Location getLeaderboardLocation() {
        return leaderboard;
    }

    public void updateHologram() {
        recordsHologram.updateHologram();
    }

    public RecordsHologram getRecordsHologram() {
        return recordsHologram;
    }
}
