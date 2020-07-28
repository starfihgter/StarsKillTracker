package net.stardevelopments.starskilltracker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Plugin plugin;
    public static FileLoader levelsLoader;

    @Override
    public void onEnable() {
        System.out.println("Star's Kill Tracker by Starfihgter has been enabled!");
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new EventTracker(), this);
        levelsLoader = new FileLoader("levels.yml");
        levelsLoader.reloadUserRecord();
    }

    @Override
    public void onDisable() {
        System.out.println("Star's Kill Tracker by Starfihgter has been un-enabled!");
    }
}
