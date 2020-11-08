package me.kmaxi.parkourtimer.commands;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomTeleportLocationCommands implements CommandExecutor {
    private final ParkourTimerMain plugin;

    public CustomTeleportLocationCommands(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length == 2) {
            if (args[0].equals(plugin.commandsManager.cmd7)) {
                plugin.blocksConfig.addItem(args[1], ((Player) sender).getLocation());
                sender.sendMessage(ChatColor.GREEN + "ADDED NEW TELEPORT LOCATION");
                sender.sendMessage(ChatColor.GREEN + "You can modify the material and text in " + ChatColor.WHITE + "blocks.yml" + ChatColor.GREEN + " file");
                return true;
            }

        }

        return false;
    }
}
