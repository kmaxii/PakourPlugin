package me.kmaxi.parkourtimer.commands;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkourCommands implements CommandExecutor {
    private final ParkourTimerMain plugin;

    public ParkourCommands(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("setupParkour")){
            return true;
        }
        if (args.length == 0){
            this.sendCommands(sender);
            return true;
        }
        if (args.length == 1){
            if (args[0].equals("reload")){
                this.reload(player);
            }
        }
        if (args.length == 2){
            if (args[1].equals(plugin.commandsManager.cmd1)){
                setStart(player, args[0]);
                return true;
            }
            if (args[1].equals(plugin.commandsManager.cmd2)){
                setEnd(player, args[0]);
                return true;
            }
            if (args[1].equals(plugin.commandsManager.cmd3)){
                setYLimit(player, args[0]);
                return true;
            }
            if (args[1].equals(plugin.commandsManager.cmd4)){
                setTeleport(player, args[0]);
                return true;
            }
            if (args[1].equals(plugin.commandsManager.cmd5)){
                printLeaderboard(player, args[0]);
                return true;
            }
            if (args[1].equals(plugin.commandsManager.cmd6)){
                setLeaderboard(player, args[0]);
                return true;
            }
        }
        return false;
    }

    private void sendCommands(CommandSender sender){
        sender.sendMessage(ChatColor.YELLOW + "----------" + ChatColor.WHITE + " Commands: " + ChatColor.YELLOW + "-------------------");
        sender.sendMessage(ChatColor.GOLD + "/parkour (parkour name) " + plugin.commandsManager.cmd1 + ChatColor.WHITE + " sets the start of a parkour");
        sender.sendMessage(ChatColor.GOLD + "/parkour (parkour name) " + plugin.commandsManager.cmd2 + ChatColor.WHITE + " sets the end of a parkour");
        sender.sendMessage(ChatColor.GOLD + "/parkour (parkour name) " + plugin.commandsManager.cmd3 + ChatColor.WHITE + " sets the y level of a parkour");
        sender.sendMessage(ChatColor.GOLD + "/parkour (parkour name) " + plugin.commandsManager.cmd4 + ChatColor.WHITE + " sets the teleport location if a player fails");
        sender.sendMessage(ChatColor.GOLD + "/parkour (parkour name) " + plugin.commandsManager.cmd6 + ChatColor.WHITE + " sets the leaderboard location");
        sender.sendMessage(ChatColor.GOLD + "/parkour (parkour name) " + plugin.commandsManager.cmd5 + ChatColor.WHITE + " checks the leaderboard of a parkour");
        sender.sendMessage(ChatColor.GOLD + "/parkour reload reloads the plugin");



    }

    private void setStart(Player player, String parkour){
        plugin.getConfig().set(parkour + ".start", player.getLocation());
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Set start for " + parkour + " parkour");
    }

    private void setEnd(Player player, String parkour){
        plugin.getConfig().set(parkour + ".end", player.getLocation());
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Set end for " + parkour + " parkour");
    }

    private void setYLimit(Player player, String parkour){
        plugin.getConfig().set(parkour + ".y", player.getLocation().getY());
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Set the y level for " + parkour + " parkour");
    }
    private void reload(Player player){
        plugin.initializeParkours();
        player.sendMessage(ChatColor.GREEN + "reloaded plugin!");
    }

    private void setTeleport(Player player, String parkour){
        plugin.getConfig().set(parkour + ".teleport", player.getLocation());
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Set the teleport location for the " + parkour + " parkour");
    }

    private void setLeaderboard(Player player, String parkour){
        Location location = player.getLocation();
        location.setY(location.getY() + 3);
        plugin.getConfig().set(parkour + ".leaderboard", location);
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Set the leaderboard location for the " + ChatColor.WHITE + parkour + ChatColor.GREEN + " parkour");
    }

    private void printLeaderboard(Player player, String parkour){
        for (ParkourManager parkourManager : plugin.parkours) {
            if (parkourManager.getName().equals(parkour)) {
                if (parkourManager.getRecords().size() == 0){
                    player.sendMessage(ChatColor.RED + "No records.");
                    break;
                }
                AtomicInteger i = new AtomicInteger(1);
                parkourManager.getRecords().forEach(record ->{
                    player.sendMessage(ChatColor.WHITE + "" + i + ": " + ChatColor.GOLD + record.getPlayerName() + ChatColor.WHITE + " " + record.getTime() + "s.");
                    i.getAndIncrement();
                });

            }
        }
    }

}
