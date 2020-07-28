package net.stardevelopments.starskilltracker;

import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.tools.doclint.Entity;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.util.EventListener;
import java.util.List;

public class EventTracker implements Listener {
    @EventHandler
    public void onDeathEvent(EntityDeathEvent event){
        String prefix = "§3§lWiser§b§lMC §f» ";
        FileConfiguration levels = Main.levelsLoader.getUserRecord();
        FileConfiguration userRecord = Main.userRecord.getUserRecord();
        LivingEntity deadEntity = event.getEntity();
        if (deadEntity.getKiller() != null){
            Player player = deadEntity.getKiller().getPlayer();
            int currentLevel = userRecord.getInt(player.getUniqueId().toString() + ".level", 0);
            int kills = userRecord.getInt(player.getUniqueId().toString() + ".points", 0);
            String[] targets = (String[]) levels.getStringList("#" + currentLevel + ".mob-kill-list").toArray();
            for (String target : targets) {
                if (deadEntity.getType() == EntityType.valueOf(target.toUpperCase())) {
                    kills++;
                    if (kills >= levels.getInt("#" + currentLevel + ".kill-number")) {
                        currentLevel++;
                        kills = 0;
                        player.sendMessage(prefix + "You are now on level " + currentLevel + "!");
                        player.sendMessage(prefix + "To progress further, you must kill " + levels.getInt("#" + currentLevel + ".kill-number") + " " + levels.getInt("#" + currentLevel + ".list-description"));
                        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
                        User user = provider.getProvider().getUserManager().getUser(player.getUniqueId());
                        String[] permissions = (String[]) levels.getStringList("#" + currentLevel + ".permissions").toArray();
                        for (String permission : permissions){
                            Node node = Node.builder(permission).value(true).build();
                            user.data().add(node);
                            provider.getProvider().getUserManager().saveUser(user);
                        }
                        userRecord.set(player.getUniqueId().toString() + ".level", currentLevel);
                    }
                    userRecord.set(player.getUniqueId().toString() + ".points", kills);
                    if (userRecord.getBoolean(player.getUniqueId().toString() + ".message", false)) {
                        int requirement = levels.getInt("#" + currentLevel + ".kill-number");
                        int description = levels.getInt("#" + currentLevel + ".list-description");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(kills + " out of " + requirement + " " + description + " kills"));
                    }
                }
            }
        }
    }
}
