package me.kmaxi.parkourtimer.records;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.utils.Utils;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class RecordsHologram {
    private final ParkourManager parkour;
    private final Hologram hologram;
    private final ParkourTimerMain plugin;


    public RecordsHologram(ParkourTimerMain plugin, ParkourManager parkour) {
        this.parkour = parkour;
        this.plugin = plugin;
        hologram = HologramsAPI.createHologram(plugin, parkour.getLeaderboardLocation());
        updateHologram();
    }

    public void updateHologram() {
        ArrayList<RecordTime> records = parkour.getRecords();
        hologram.clearLines();
        TextLine textLineTop = hologram.appendTextLine(Utils.color(plugin.messegesConfig.formatPlaceHoldersLeaderboard("leaderboard.info", parkour.getName(), null, null)));
        int placeCounter = 1;
        for (int i = 0; i <= 9; i++) {
            if (records.size() - 1 >= i) {
                RecordTime recordTime = records.get(i);
                TextLine text = hologram.appendTextLine(Utils.color(plugin.messegesConfig.formatPlaceHoldersLeaderboard("leaderboard.records", parkour.getName(), placeCounter, recordTime)));
                placeCounter++;
                continue;
            }
            TextLine text = hologram.appendTextLine("----");
            placeCounter++;
        }
    }

    public void delete() {
        hologram.delete();
    }


}
