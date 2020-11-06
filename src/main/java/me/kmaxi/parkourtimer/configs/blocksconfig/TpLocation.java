package me.kmaxi.parkourtimer.configs.blocksconfig;

import me.kmaxi.parkourtimer.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TpLocation {

    private final String text;
    private final Material material;
    private final Location location;
    private final String key;
    private final int slot;
    private ItemStack item;

    public TpLocation(String text, Material material, Location location, String key, int slot){
        this.material = material;
        this.text = text;
        this.location = location;
        this.key = key;
        this.slot = slot;
        makeItem();
    }


    private void makeItem(){
        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.color(text));
        item.setItemMeta(meta);
    }

    public String getText() {
        return text;
    }

    public Material getMaterial() {
        return material;
    }

    public Location getLocation() {
        return location;
    }

    public String getKey() {
        return key;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }
}
