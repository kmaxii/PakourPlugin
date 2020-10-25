package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class movementCheck implements Listener {
    private final ParkourTimerMain plugin;

    public movementCheck(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Location playerLocation = event.getPlayer().getLocation();
        if (!(plugin.players.get(event.getPlayer()).getParkour() == null)){
            if (playerLocation.distance(plugin.players.get(event.getPlayer()).getParkour().getEnd()) <= 2){
                finishParkour(event.getPlayer());
                return;
            }
        }
        plugin.parkours.forEach(parkour ->{
            if (playerLocation.distance(parkour.getStart()) <= 2){
                startParkour(event.getPlayer(), parkour);
            }
        });
    }

    private void startParkour(Player player, ParkourManager parkour){
        PlayerManager playerManager = plugin.players.get(player);
        if (!(playerManager.getParkour() == null)){
            return;
        }
        player.setGameMode(GameMode.ADVENTURE);
        Items.addParkourItems(player);
        playerManager.setCheckPoint(null);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("starting " + parkour.getName() + " parkour"));
        playerManager.setParkour(parkour);
        playerManager.setCurrentParkourTime(0);
        timer(playerManager);
    }

    private void finishParkour(Player player){
        PlayerManager playerManager = plugin.players.get(player);
        player.getInventory().clear();
        double timeItTook = playerManager.getCurrentParkourTime();
        DecimalFormat df = new DecimalFormat("0.00");
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName()
                + ChatColor.WHITE + " finished the " + playerManager.getParkour().getName() + " parkour in "
                + ChatColor.YELLOW + df.format(timeItTook) + ChatColor.WHITE + " seconds");
        Bukkit.broadcastMessage(" ");
        playerManager.finishParkour(timeItTook);
        playerManager.setParkour(null);
    }

    private void timer(PlayerManager playerManager){
        Player player = playerManager.getPlayer();
        new BukkitRunnable(){
            double time = 0;
            final ParkourManager parkour = playerManager.getParkour();
            DecimalFormat df = new DecimalFormat("0.00");
            @Override
            public void run() {
                playerManager.setCurrentParkourTime(time);
                time += 0.05;
                df.setMinimumFractionDigits(2);
                time = Double.parseDouble(df.format(time));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.LIGHT_PURPLE + "" + time));
                if(player.getLocation().getY() <= parkour.getY()){
                    if (playerManager.getCheckPoint() == null){
                        plugin.functions.teleportToStart(playerManager, parkour);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "You failed!"));
                        cancel();
                        return;
                    }
                    player.teleport(playerManager.getCheckPoint());
                    playerManager.setCheckPoint(null);

                }
                if (!(parkour.equals(playerManager.getParkour()))){
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

}
