package me.kmaxi.parkourtimer;

import me.kmaxi.parkourtimer.commands.CommandsManager;
import me.kmaxi.parkourtimer.configs.MessegesConfig;
import me.kmaxi.parkourtimer.listeners.*;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ParkourTimerMain extends JavaPlugin {
    public CommandsManager commandsManager;
    public HashMap<Player, PlayerManager> players;
    public ArrayList<ParkourManager> parkours;
    public Functions functions;
    public MessegesConfig messegesConfig;
    public Items items;


    @Override
    public void onEnable() {
        this.initialize();
        Bukkit.getPluginManager().registerEvents(new movementCheck(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinServer(this), this);
        Bukkit.getPluginManager().registerEvents(new CheckForCommands(this), this);
        Bukkit.getPluginManager().registerEvents(new CheckForItemInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new CancelMount(this), this);
        Bukkit.getPluginManager().registerEvents(new CancelPvp(this), this);
        Bukkit.getPluginManager().registerEvents(new CancelDroppedItem(this), this);
    }

    @Override
    public void onDisable() {
        deleteHolograms();
    }


    private void initialize() {
        this.commandsManager = new CommandsManager(this);
        this.players = new HashMap<>();
        this.parkours = new ArrayList<>();
        this.functions = new Functions(this);
        this.messegesConfig = new MessegesConfig(this);
        this.items = new Items(this);
        initializeParkours();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getOnlinePlayers().forEach(player -> players.put(player, new PlayerManager(player, this)));
    }

    public void initializeParkours() {
        parkours.clear();
        Set<String> allParkours = getConfig().getKeys(false);
        for (String key : allParkours) {
            if (key.contains(".")) {
                continue;
            }
            if (!checkInformation(key)) {
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

    public void deleteHolograms() {
        parkours.forEach(parkourManager -> parkourManager.getRecordsHologram().delete());
    }

    public Boolean checkInformation(String parkourName) {
        boolean tobeReturned = true;
        if (!getConfig().contains(parkourName + ".start")) {
            Bukkit.broadcastMessage(ChatColor.RED + parkourName + " is missing start");
            tobeReturned = false;
        }
        if (!getConfig().contains(parkourName + ".end")) {
            Bukkit.broadcastMessage(ChatColor.RED + parkourName + " is missing end");
            tobeReturned = false;
        }
        if (!getConfig().contains(parkourName + ".teleport")) {
            Bukkit.broadcastMessage(ChatColor.RED + parkourName + " is missing teleport location");
            tobeReturned = false;
        }
        if (!getConfig().contains(parkourName + ".y")) {
            Bukkit.broadcastMessage(ChatColor.RED + parkourName + " is missing y location for fail");
            tobeReturned = false;
        }
        if (!getConfig().contains(parkourName + ".leaderboard")) {
            Bukkit.broadcastMessage(ChatColor.RED + parkourName + " is missing leaderboard location");
            tobeReturned = false;
        }
        return tobeReturned;
    }


}
