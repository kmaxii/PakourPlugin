package me.kmaxi.parkourtimer.records;


public class RecordTime implements Comparable<RecordTime>{
    private final double time;
    private final String playerName;

    public RecordTime(String playerName, double time) {
        this.playerName = playerName;
        this.time = time;
    }
    public RecordTime(String string){
        String[] strings = string.split("%");
        time = Double.parseDouble(strings[1]);
        playerName = strings[0];
    }

    public String getInfoAsString(){
        return playerName + "%" + time;
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
