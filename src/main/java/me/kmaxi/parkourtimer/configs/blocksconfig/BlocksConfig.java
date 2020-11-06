package me.kmaxi.parkourtimer.configs.blocksconfig;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BlocksConfig {
    private final ParkourTimerMain plugin;
    private File customTeleportLoc;
    private FileConfiguration customTeleportLocConfig;
    public HashMap<String, TpLocation> tpLocations;

    public BlocksConfig(ParkourTimerMain plugin) {
        this.plugin = plugin;
        tpLocations = new HashMap<>();
        createRecordConfig();
        initialize();
    }


    public void createRecordConfig() {
        customTeleportLoc = new File(plugin.getDataFolder(), "blocks.yml");
        if (!customTeleportLoc.exists()) {
            customTeleportLoc.getParentFile().mkdirs();
            plugin.saveResource("blocks.yml", false);
        }

        customTeleportLocConfig = new YamlConfiguration();
        try {
            customTeleportLocConfig.load(customTeleportLoc);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.broadcastMessage("failed to load messageConfig");
            e.printStackTrace();
        }
    }

    public FileConfiguration getCustomTeleportLocConfig() {
        return this.customTeleportLocConfig;
    }

    private void initialize() {
        for (String key : customTeleportLocConfig.getKeys(false)) {
            String text = customTeleportLocConfig.getString(keyToText(key));
            Material material = Material.getMaterial(customTeleportLocConfig.getString(keyToMaterial(key)));
            Location location = (Location) customTeleportLocConfig.get(keyToLocation(key));
            int slot = customTeleportLocConfig.getInt(keyToSlot(key));
            tpLocations.put(key, new TpLocation(text, material, location, key, slot));
        }
    }

    public void Save() {
        tpLocations.values().forEach(tpLocation -> {
            String key = tpLocation.getKey();
            customTeleportLocConfig.set(keyToText(key), tpLocation.getText());
            customTeleportLocConfig.set(keyToMaterial(key), tpLocation.getMaterial().toString());
            customTeleportLocConfig.set(keyToLocation(key), tpLocation.getLocation());
            customTeleportLocConfig.set(keyToSlot(key), tpLocation.getSlot());
        });
        try {
            customTeleportLocConfig.save(customTeleportLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String keyToText(String string) {
        return string + ".text";
    }

    private String keyToMaterial(String string) {
        return string + ".material";
    }

    private String keyToLocation(String string) {
        return string + ".location";
    }

    private String keyToSlot(String string) {
        return string + ".slot";
    }

    ;

    public void addItem(String key, Location location) {
        Material material = Utils.getRandomMaterial();
        int untakenSlot = getUntakenSlot();
        tpLocations.put(key, new TpLocation(key, material, location, key, untakenSlot));
        Save();
    }


    public void addItem(String key, Location location, String displayName) {
        Material material = Utils.getRandomMaterial();
        int untakenSlot = getUntakenSlot();
        tpLocations.put(key, new TpLocation(displayName, material, location, key, untakenSlot));
        Save();
    }

    public TpLocation getLocation(String key) {
        return tpLocations.get(key);
    }

    public HashMap<String, TpLocation> getTpLocations() {
        return tpLocations;
    }

    private int getUntakenSlot(){
        ArrayList<Integer> takenInventorySlots = new ArrayList<>();
        for(TpLocation tpLocation : tpLocations.values()){
            if (tpLocation.getKey().equals("leaveParkour")
                    || tpLocation.getKey().equals("setCheckpoint")
                    || tpLocation.getKey().equals("teleportToCheckpoint")
                    || tpLocation.getKey().equals("hidePlayers")
                    || tpLocation.getKey().equals("showPlayers")) {
                continue;
            }
            takenInventorySlots.add(tpLocation.getSlot());
        }
        int notTakenSlot = 0;
        for (int i = 0; i < takenInventorySlots.size(); i++) {
            if (takenInventorySlots.contains(notTakenSlot)) {
                notTakenSlot++;
                continue;
            }
            return notTakenSlot;
        }
        return notTakenSlot;

    }
}
