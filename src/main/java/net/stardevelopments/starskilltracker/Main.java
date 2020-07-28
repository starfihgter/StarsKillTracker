package net.stardevelopments.starskilltracker;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    FileConfiguration config = this.getConfig();
    public static Plugin plugin;
    public static FileLoader levelsLoader;
    public static FileLoader userRecord;

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
        levelsLoader.getUserRecord().addDefault("total levels", 1);
        levelsLoader.getUserRecord().addDefault("#0.permissions", new String[]{"epicspawners.place.cow", "epicspawners.place.pig"});
        levelsLoader.getUserRecord().addDefault("#0.mob-kill-list", new String[]{"COW", "PIG"});
        levelsLoader.getUserRecord().addDefault("#0.kill-number", 100);
        levelsLoader.getUserRecord().addDefault("#0.list-description", "Cows and Pigs");
        levelsLoader.getUserRecord().options().copyDefaults();
        levelsLoader.saveCustomConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("Star's Kill Tracker by Starfihgter has been un-enabled!");
        userRecord.saveCustomConfig();
    }
}
