package net.stardevelopments.starskilltracker;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        FileConfiguration levels = Main.levelsLoader.getUserRecord();
        FileConfiguration userRecord = Main.userRecord.getUserRecord();
        String prefix = Main.prefix;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!(userRecord.getBoolean(player.getUniqueId().toString() + ".message", false))) {
                player.sendMessage(prefix + "Enabled Action-Bar message!");
                String message = Main.doPlaceholders(Main.plugin.getConfig().getString("bar-message"), player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                userRecord.set(player.getUniqueId().toString() + ".message", true);
                return true;
            } else {
                player.sendMessage(prefix + "Disabled Action-Bar message!");
                userRecord.set(player.getUniqueId().toString() + ".message", false);
                return true;
            }
        } else {
            sender.sendMessage("This command can only be used by a player!");
            return true;
        }
    }
}
