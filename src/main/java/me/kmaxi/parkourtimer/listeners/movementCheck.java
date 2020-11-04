package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import me.kmaxi.parkourtimer.utils.Items;
import me.kmaxi.parkourtimer.utils.Utils;
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
    public void onMove(PlayerMoveEvent event) {
        Location playerLocation = event.getPlayer().getLocation();
        if (!(plugin.players.get(event.getPlayer()).getParkour() == null)) {
            if (playerLocation.distance(plugin.players.get(event.getPlayer()).getParkour().getEnd()) <= 1.5) {
                finishParkour(event.getPlayer());
                return;
            }
        }
        plugin.parkours.forEach(parkour -> {
            if (playerLocation.distance(parkour.getStart()) <= 1.5) {
                startParkour(event.getPlayer(), parkour);
            }
        });
    }

    private void startParkour(Player player, ParkourManager parkour) {
        PlayerManager playerManager = plugin.players.get(player);
        if (!(playerManager.getParkour() == null)) {
            return;
        }
        if (player.isInsideVehicle()) {
            player.leaveVehicle();
        }
        player.setGameMode(GameMode.ADVENTURE);
        Items.addParkourItems(player, plugin);
        playerManager.setCheckPoint(null);
        playerManager.setParkour(parkour);
        playerManager.setCurrentParkourTime(0);
        timer(playerManager);
    }

    private void finishParkour(Player player) {
        PlayerManager playerManager = plugin.players.get(player);
        player.getInventory().clear();
        double timeItTook = playerManager.getCurrentParkourTime();
        DecimalFormat df = new DecimalFormat("0.00");
        Bukkit.broadcastMessage(" ");
        df.setMinimumFractionDigits(2);
        Bukkit.broadcastMessage(Utils.color(plugin.messegesConfig.formatPlaceholders("broadcast.finishParkour", player)));
        Bukkit.broadcastMessage(" ");
        ParkourManager parkourManager = playerManager.getParkour();
        playerManager.setParkour(null);
        playerManager.finishParkour(timeItTook, parkourManager);
    }

    private void timer(PlayerManager playerManager) {
        Player player = playerManager.getPlayer();
        new BukkitRunnable() {
            double time = 0;
            final ParkourManager parkour = playerManager.getParkour();
            final DecimalFormat df = new DecimalFormat("0.00");
            final Boolean useEXP = plugin.messegesConfig.getConfigBooleanData().get("showTimeOnEXP");
            final Boolean useActionBar = plugin.messegesConfig.getConfigBooleanData().get("actionBar.useActionBar");

            @Override
            public void run() {
                if (!(parkour.equals(playerManager.getParkour()))) {
                    if (useEXP) {
                        Utils.resetEXP(player);
                    }
                    Utils.showPlayers(player, plugin);
                    Items.addLobbyItems(player, plugin);
                    cancel();
                    return;
                }
                time += 0.05;
                df.setMinimumFractionDigits(2);
                time = Double.parseDouble(df.format(time));
                player.setTotalExperience((int) time);
                playerManager.setCurrentParkourTime(time);
                if (useEXP) {
                    Utils.setEXP(time, player);
                }
                if (useActionBar) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.messegesConfig.formatPlaceholders("actionBar.timer", player)));
                }
                if (player.getLocation().getY() <= parkour.getY()) {
                    if (playerManager.getCheckPoint() == null) {
                        plugin.functions.teleportToStart(playerManager, parkour);
                        player.getInventory().clear();
                        if (useActionBar) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.messegesConfig.formatPlaceholders("actionBar.failedParkour", player)));
                        }
                        if (useEXP) {
                            Utils.resetEXP(player);
                        }
                        Utils.showPlayers(player, plugin);
                        Items.addLobbyItems(player, plugin);
                        cancel();
                        return;
                    }
                    player.teleport(playerManager.getCheckPoint());
                    playerManager.setCheckPoint(null);
                    player.getInventory().remove(Items.getItem("teleportToCheckpoint", plugin));
                }
                if (checkIfPlayerLeft(player)) {
                    playerManager.setCheckPoint(null);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private Boolean checkIfPlayerLeft(Player player) {
        return !player.isOnline() || !player.getWorld().equals(plugin.players.get(player).getParkour().getEnd().getWorld());
    }


}
