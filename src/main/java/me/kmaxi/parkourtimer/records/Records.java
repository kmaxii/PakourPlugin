package me.kmaxi.parkourtimer.records;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class Records {
    private final ParkourTimerMain plugin;
    private ArrayList<RecordTime> records;
    private final ParkourManager parkour;

    public Records(ParkourTimerMain plugin, ParkourManager parkour) {
        this.plugin = plugin;
        this.parkour = parkour;
        this.initializeRecords();
    }

    private void initializeRecords() {
        records = new ArrayList<>();
        for (int i = 1; i <= 10; i++){
            if (plugin.getConfig().contains(parkour.getName() + ".records." + i)){
                if (plugin.getConfig().get(parkour.getName() + ".records." + i) == null){
                    return;
                }
                records.add(new RecordTime((String) plugin.getConfig().get(parkour.getName() + ".records." + i)));
            }
        }
    }
    public void completedParkour(Player player, double time){
        if (records.size() <= 10){
            addRecord(player, time);
            return;
        }
        if (records.get(9).getTime() < time){
            addRecord(player, time);
        }
    }

    public void saveRecords(){
        AtomicInteger i = new AtomicInteger(1);
        records.forEach(record -> {
            plugin.getConfig().set(parkour.getName() + ".records." + i, record.getInfoAsString());
            i.getAndIncrement();
        });
        plugin.saveConfig();
    }

    public void addRecord(Player player, double time){
        records.add(new RecordTime(player.getName(), time));
        records.sort(Comparator.naturalOrder());
        if (records.size() > 10){
            records.remove(10);
        }
        saveRecords();
        parkour.updateHologram();
    }



    public ArrayList<RecordTime> getRecords() {
        return records;
    }


}