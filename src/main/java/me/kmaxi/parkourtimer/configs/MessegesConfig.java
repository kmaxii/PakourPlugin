package me.kmaxi.parkourtimer.configs;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.records.RecordTime;
import me.kmaxi.parkourtimer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MessegesConfig {
    private final ParkourTimerMain plugin;
    private File messagesConfigFile;
    private FileConfiguration messagesConfig;

    public MessegesConfig(ParkourTimerMain plugin) {
        this.plugin = plugin;
        createRecordConfig();
    }

    public void createRecordConfig(){
        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()){
            messagesConfigFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesConfigFile);
        } catch (IOException | InvalidConfigurationException e){
            Bukkit.broadcastMessage("failed to load messageConfig");
            e.printStackTrace();
        }
    }

    public FileConfiguration getMessagesConfig(){
        return this.messagesConfig;
    }

    public String formatPlaceholders(String path, Player player) {
        PlayerManager playerManager = plugin.players.get(player);
        String out = getMessagesConfig().getString(path);
        if (out.contains("%timer%")){
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMinimumFractionDigits(2);
            out = out.replaceAll("%timer%", df.format(playerManager.getCurrentParkourTime()));
        }
        if(out.contains("%playerName%")){
            out = out.replaceAll("%playerName%", player.getName());
        }
        if(out.contains("%parkourName")){
            out = out.replaceAll("%parkourName%", playerManager.getParkour().getName());
        }
        out = Utils.color(out);
        return out;
    }

    public String formatPlaceHoldersLeaderboard(String path, String parkourName, Integer position, RecordTime recordTime){
        String out = getMessagesConfig().getString(path);
        if (out == null){
            out = "null";
        }
        if (out.contains("%parkourName%")){
            out = out.replaceAll("%parkourName%",parkourName);
        }
        if(out.contains("%position%")){
            out = out.replaceAll("%position%", String.valueOf(position));
        }
        if(out.contains("%playerName%")){
            out = out.replaceAll("%playerName%", recordTime.getPlayerName());
        }
        if(out.contains("%timer%")){
            out = out.replaceAll("%timer%", String.valueOf(recordTime.getTime()));
        }
        return out;
    }
}
