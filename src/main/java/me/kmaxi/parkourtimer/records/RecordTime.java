package me.kmaxi.parkourtimer.records;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class RecordTime implements Comparable<RecordTime>{
    private final double time;
    private final String playerName;

    public RecordTime(String playerName, double time) {
        this.playerName = playerName;
        this.time = time;
    }
    public RecordTime(String string){
        if (!string.contains("_")){
            Bukkit.broadcastMessage(ChatColor.RED + "------------------------------------------");
            Bukkit.broadcastMessage(ChatColor.RED + "String " + string + " in config does not contain an _");
            Bukkit.broadcastMessage(ChatColor.RED + "Please fix this.");
            Bukkit.broadcastMessage(ChatColor.RED + "------------------------------------------");
        }
        String[] strings = string.split("_");
        time = Double.parseDouble(strings[1]);
        playerName = strings[0];
    }

    public String getInfoAsString(){
        return playerName + "_" + time;
    }

    public double getTime() {
        return time;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int compareTo(RecordTime o){
        return Double.compare(this.getTime(), o.getTime());
    }
}
