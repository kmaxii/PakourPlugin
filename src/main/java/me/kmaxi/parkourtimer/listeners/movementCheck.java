package me.kmaxi.parkourtimer.listeners;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import me.kmaxi.parkourtimer.managers.PlayerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
        player.setGameMode(GameMode.ADVENTURE);
        PlayerManager playerManager = plugin.players.get(player);
        if (playerManager.getJustStartedParkour()){
            return;
        }
        player.sendMessage("Starting parkour " + parkour.getName());
        playerManager.setParkour(parkour);
        playerManager.setCurrentParkourTime(0);
        playerManager.setJustStartedParkour(true);
        timer(playerManager);


    }

    private void finishParkour(Player player){
        PlayerManager playerManager = plugin.players.get(player);
        double timeItTook = playerManager.getCurrentParkourTime();
        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName()
                + ChatColor.WHITE + " finished the " + playerManager.getParkour().getName() + " parkour in "
                + ChatColor.YELLOW + timeItTook + ChatColor.WHITE + " seconds");
        playerManager.setParkour(null);
    }

    private void timer(PlayerManager playerManager){
        Player player = playerManager.getPlayer();
        new BukkitRunnable(){
            double time = 0;
            final ParkourManager parkour = playerManager.getParkour();
            @Override
            public void run() {
                playerManager.setCurrentParkourTime(time);
                time += 0.05;
                if(player.getLocation().getY() <= parkour.getY()){
                    player.teleport(parkour.getTeleport());
                    player.sendMessage(ChatColor.RED + "You failed!");
                    cancel();
                }
                if (!(parkour.equals(playerManager.getParkour()))){
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

}
