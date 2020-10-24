package me.kmaxi.parkourtimer.records;

public class RecordTime implements Comparable<RecordTime>{
    private double time;
    private String playerName;

    public RecordTime(String playerName, double time) {
        this.playerName = playerName;
        this.time = time;
    }
    public RecordTime(String string){
        String[] strings = string.split("_");
        time = Double.parseDouble(strings[1]);
        playerName = strings[0];
    }

    public double getTime() {
        return time;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public int compareTo(RecordTime o){
        return Double.compare(this.getTime(), o.getTime());
    }
}
