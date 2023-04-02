package f3f5.helpcommand

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {
    private val config: FileConfiguration = getConfig()

    override fun onEnable() {
        saveDefaultConfig()
        Bukkit.getServer().pluginManager.registerEvents(this, this)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onCommandPreprocess(evt: PlayerCommandPreprocessEvent) {
        if (evt.message.toLowerCase().startsWith("/help")) {
            config.getList("help").forEach { b ->
                evt.player.sendMessage(
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        b as String
                    )
                )
            }
            evt.isCancelled = true
        }
    }
}
