package net.stardevelopments.starskilltracker;

import com.sun.org.apache.xerces.internal.xs.StringList;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Main extends JavaPlugin {
    FileConfiguration config = this.getConfig();
    public static Plugin plugin;
    public static FileLoader levelsLoader;
    public static FileLoader userRecord;
    public static String prefix;

    @Override
    public void onEnable() {
        System.out.println("Star's Kill Tracker by Starfihgter has been enabled!");
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new EventTracker(), this);
        getCommand("level").setExecutor(new Command());
        getCommand("showlevel").setExecutor(new CommandToggle());
        levelsLoader = new FileLoader("levels.yml");
        levelsLoader.reloadUserRecord();
        userRecord = new FileLoader("userRecord.yml");
        userRecord.reloadUserRecord();
        levelsLoader.getUserRecord().addDefault("total-levels", 1);
        levelsLoader.getUserRecord().addDefault("#0.permissions", new String[]{"epicspawners.place.cow", "epicspawners.place.pig"});
        levelsLoader.getUserRecord().addDefault("#0.mob-kill-list", new String[]{"COW", "PIG"});
        levelsLoader.getUserRecord().addDefault("#0.kill-number", 100);
        levelsLoader.getUserRecord().addDefault("#0.list-description", "Cows and Pigs");
        levelsLoader.getUserRecord().options().copyDefaults(true);
        levelsLoader.saveCustomConfig();
        config.addDefault("prefix", "§3§lWiser§b§lMC §f» ");
        config.addDefault("level-message", "You are currently on level %level% and you have killed %kills% out of %requirement% %description%");
        config.addDefault("bar-message", "%kills% out of %requirement% %description% kills");
        config.addDefault("levelup-message-1", "You are now on level %level%!");
        config.addDefault("levelup-message-2", "To progress further, you must kill %requirement% %description%!");
        config.options().copyDefaults(true);
        plugin.saveConfig();
        prefix = doPlaceholders(config.getString("prefix"), null);
    }

    public static String doPlaceholders(String message, @Nullable Player player){
        FileConfiguration levels = levelsLoader.getUserRecord();
        FileConfiguration userRecord = Main.userRecord.getUserRecord();
        if (player == null){
        message = message.replaceAll("&", "§");
        return message;
        }
        int currentLevel = userRecord.getInt(player.getUniqueId().toString() + ".level", 0);
        int kills = userRecord.getInt(player.getUniqueId().toString() + ".points", 0);
        int requirement = levels.getInt("#" + currentLevel + ".kill-number");
        String description = levels.getString("#" + currentLevel + ".list-description");
        message = message.replace("%level%", Integer.toString(currentLevel));
        message = message.replace("%kills%", Integer.toString(kills));
        message = message.replace("%description%", description);
        message = message.replace("%requirement%", Integer.toString(requirement));
        return message;
    }
    @Override
    public void onDisable() {
        System.out.println("Star's Kill Tracker by Starfihgter has been un-enabled!");
        userRecord.saveCustomConfig();
    }
}
