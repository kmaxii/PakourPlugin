package me.kmaxi.parkourtimer.configs;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.records.RecordTime;
import me.kmaxi.parkourtimer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Objects;

public class MessegesConfig {
    private final ParkourTimerMain plugin;
    private File messagesConfigFile;
    private FileConfiguration messagesConfig;
    private HashMap<String, String> configData;
    private HashMap<String, Material> configMaterialData;
    private HashMap<String, Boolean> configBooleanData;

    public MessegesConfig(ParkourTimerMain plugin) {
        configData = new HashMap<>();
        configMaterialData = new HashMap<>();
        configBooleanData = new HashMap<>();
        this.plugin = plugin;
        createRecordConfig();
        initializeData();
    }

    public void createRecordConfig() {
        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.broadcastMessage("failed to load messageConfig");
            e.printStackTrace();
        }
    }

    public FileConfiguration getMessagesConfig() {
        return this.messagesConfig;
    }

    public void saveConifg() {
        try {
            for (String key : configMaterialData.keySet()) {
                Material toBeSaved = configMaterialData.get(key);
                if (toBeSaved == null) {
                    continue;
                }
                messagesConfig.set(key, toBeSaved.toString());
            }
            for (String key1 : configData.keySet()) {
                if (configData.get(key1) == null || !key1.contains("text")) {
                    continue;
                }
                messagesConfig.set(key1, configData.get(key1));
            }
            messagesConfig.save(messagesConfigFile);
        } catch (IOException e) {
            Bukkit.broadcastMessage(ChatColor.RED + "COULDN'T SAVE CUSTOM CONFIG");
            e.printStackTrace();
        }
    }


    public String formatPlaceholders(String path, Player player) {
        PlayerManager playerManager = plugin.players.get(player);
        String out = configData.get(path);
        if (out.contains("%timer%")) {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMinimumFractionDigits(2);
            out = out.replaceAll("%timer%", df.format(playerManager.getCurrentParkourTime()));
        }
        if (out.contains("%playerName%")) {
            out = out.replaceAll("%playerName%", player.getName());
        }
        if (out.contains("%parkourName")) {
            out = out.replaceAll("%parkourName%", playerManager.getParkour().getName());
        }
        out = Utils.color(out);
        return out;
    }

    public String formatPlaceHoldersLeaderboard(String path, String parkourName, Integer position, RecordTime recordTime) {
        String out = configData.get(path);
        if (out == null) {
            out = "null";
        }
        if (out.contains("%parkourName%")) {
            out = out.replaceAll("%parkourName%", parkourName);
        }
        if (out.contains("%position%")) {
            out = out.replaceAll("%position%", String.valueOf(position));
        }
        if (out.contains("%playerName%")) {
            out = out.replaceAll("%playerName%", recordTime.getPlayerName());
        }
        if (out.contains("%timer%")) {
            out = out.replaceAll("%timer%", String.valueOf(recordTime.getTime()));
        }
        return out;
    }

    public void initializeData() {
        for (String key : messagesConfig.getKeys(true)) {
            if (key.contains("material")) {
                configMaterialData.put(key, Material.getMaterial(Objects.requireNonNull(messagesConfig.getString(key))));
                continue;
            }
            if (key.equals("actionBar.useActionBar")
                    || key.equals("showTimeOnEXP")
                    || key.equals("useLobbyItems")) {
                configBooleanData.put(key, messagesConfig.getBoolean(key));
                continue;
            }
            configData.put(key, messagesConfig.getString(key));
        }
    }

    public HashMap<String, String> getConfigData() {
        return configData;
    }

    public HashMap<String, Material> getConfigMaterialData() {
        return configMaterialData;
    }

    public HashMap<String, Boolean> getConfigBooleanData() {
        return configBooleanData;
    }

}
