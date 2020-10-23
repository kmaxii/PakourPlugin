package me.kmaxi.parkourtimer.commands;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            this.sendCommands(player);
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
        }
        return false;
    }

    private void sendCommands(Player player){

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

}
