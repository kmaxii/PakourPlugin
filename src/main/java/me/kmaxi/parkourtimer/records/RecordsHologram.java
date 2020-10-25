package me.kmaxi.parkourtimer.records;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class RecordsHologram {
    private final ParkourManager parkour;
    private final Hologram hologram;


    public RecordsHologram(ParkourTimerMain plugin, ParkourManager parkour) {
        this.parkour = parkour;
        hologram = HologramsAPI.createHologram(plugin, parkour.getLeaderboardLocation());
        updateHologram();
    }

    public void updateHologram(){
        ArrayList<RecordTime> records = parkour.getRecords();
        hologram.clearLines();
        TextLine textLineTop = hologram.appendTextLine(parkour.getName() + " parkour leaderboard");
        int placeCounter = 1;
        for(int i = 0; i <= 9; i++){
            if (records.size() - 1 >= i){
                RecordTime recordTime = records.get(i);
                TextLine text = hologram.appendTextLine(placeCounter + ": " + ChatColor.GOLD +  recordTime.getPlayerName() + ChatColor.WHITE + " " + recordTime.getTime() + "s.");
                placeCounter++;
                continue;
            }
            TextLine text = hologram.appendTextLine("----");
            placeCounter++;
        }
    }

    public void delete(){
        hologram.delete();
    }


}
