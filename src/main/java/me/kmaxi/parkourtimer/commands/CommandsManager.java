package me.kmaxi.parkourtimer.commands;

import me.kmaxi.parkourtimer.ParkourTimerMain;

public class CommandsManager {
    public String cmd1 = "start";
    public String cmd2 = "end";
    public String cmd3 = "y";
    public String cmd4 = "teleport";

    private final ParkourTimerMain plugin;


    public CommandsManager(ParkourTimerMain plugin) {
        this.plugin = plugin;
        plugin.getCommand("parkour").setExecutor(new ParkourCommands(plugin));
    }

}
