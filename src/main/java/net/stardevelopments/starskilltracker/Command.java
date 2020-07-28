package net.stardevelopments.starskilltracker;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof  Player) {
            String prefix = "§3§lWiser§b§lMC §f» ";
            Player player = (Player) sender;
            FileConfiguration levels = Main.levelsLoader.getUserRecord();
            FileConfiguration userRecord = Main.userRecord.getUserRecord();
            int currentLevel = userRecord.getInt(player.getUniqueId().toString() + ".level", 0);
            int kills = userRecord.getInt(player.getUniqueId().toString() + ".points", 0);
            int requirement = levels.getInt("#" + currentLevel + ".kill-number");
            int description = levels.getInt("#" + currentLevel + ".list-description");
            player.sendMessage(prefix + "You are currently on level " + currentLevel + " and you have currently killed " + kills + " out of " + requirement + " " + description);
            return true;
        }else{
            sender.sendMessage("This command can only be used by a player!");
            return true;
        }
    }
}
