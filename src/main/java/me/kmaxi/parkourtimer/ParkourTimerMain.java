package me.kmaxi.parkourtimer;

import me.kmaxi.parkourtimer.commands.CommandsManager;
import me.kmaxi.parkourtimer.listeners.CheckForCommands;
import me.kmaxi.parkourtimer.listeners.CheckForItemInteract;
import me.kmaxi.parkourtimer.listeners.PlayerJoinServer;
import me.kmaxi.parkourtimer.listeners.movementCheck;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ParkourTimerMain extends JavaPlugin {
    public CommandsManager commandsManager;
    public HashMap<Player, PlayerManager> players;
    public ArrayList<ParkourManager> parkours;
    public Functions functions;


    @Override
    public void onEnable(){
        this.initialize();
        Bukkit.getPluginManager().registerEvents(new movementCheck(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinServer(this), this);
        Bukkit.getPluginManager().registerEvents(new CheckForCommands(this), this);
        Bukkit.getPluginManager().registerEvents(new CheckForItemInteract(this), this);
    }

    @Override
    public void onDisable(){

    }

    private void initialize(){
        this.commandsManager = new CommandsManager(this);
        this.players = new HashMap<>();
        this.parkours = new ArrayList<>();
        this.functions = new Functions(this);
        initializeParkours();
        saveConfig();
        Bukkit.getOnlinePlayers().forEach(player -> {
            players.put(player, new PlayerManager(player, this));
        });
    }

    public void initializeParkours(){
        parkours.clear();
        Set<String> allParkours = getConfig().getKeys(false);
        for (String key : allParkours) {
            if (key.contains(".")) {
                continue;
            }
            this.parkours.add(new ParkourManager((Location) getConfig().get(key + ".start"),
                    (Location) getConfig().get(key + ".end"),
                    (Location) getConfig().get(key + ".teleport"),
                    getConfig().getDouble(key + ".y"),
                    key, this,
                    (Location) getConfig().get(key + ".leaderboard")));
        }

    }
}
