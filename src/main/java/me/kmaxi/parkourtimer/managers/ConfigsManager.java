package me.kmaxi.parkourtimer.managers;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigsManager {
    private final ParkourTimerMain plugin;
    private File recordsConfigFile;
    private FileConfiguration recordsConfig;

    public ConfigsManager(ParkourTimerMain plugin) {
        this.plugin = plugin;
        createRecordConfig();
    }

    public void createRecordConfig(){
        recordsConfigFile = new File(plugin.getDataFolder(), "records.yml");
    }

    public FileConfiguration getRecordsConfig(){
        return this.recordsConfig;
    }
}
