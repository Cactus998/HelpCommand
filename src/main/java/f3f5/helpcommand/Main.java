package f3f5.helpcommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();

    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent evt) {
        if (evt.getMessage().toLowerCase().startsWith("/help")) {
            getConfig().getList("help").forEach(b -> evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', (String) b)));
            evt.setCancelled(true);
        }
    }
}