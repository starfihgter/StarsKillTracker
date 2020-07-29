package net.stardevelopments.starskilltracker;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof  Player) {
            String prefix = Main.prefix;
            Player player = (Player) sender;
            String message = Main.doPlaceholders(Main.plugin.getConfig().getString("level-message"), player);
            player.sendMessage(prefix + message);
            return true;
        }else{
            sender.sendMessage("This command can only be used by a player!");
            return true;
        }
    }
}
