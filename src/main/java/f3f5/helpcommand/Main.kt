package f3f5.helpcommand

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Main : JavaPlugin(), Listener {
    private val config: FileConfiguration = getConfig()

    override fun onEnable() {
        saveDefaultConfig()
        Bukkit.getServer().pluginManager.registerEvents(this, this)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (command.name.equals("help", ignoreCase = true)) {
            val completions = mutableListOf<String>()
            val categories = config.getConfigurationSection("").getKeys(false)
            if (args.size == 1) {
                completions.addAll(categories.filter { it.startsWith(args[0], ignoreCase = true) })
            } else if (args.size == 2) {
                val category = categories.find { it.equals(args[0], ignoreCase = true) }
                if (category != null) {
                    val messages = config.getStringList(category)
                    completions.addAll(messages.filter { it.startsWith(args[1], ignoreCase = true) })
                }
            }
            return completions
        }
        return null
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onCommandPreprocess(evt: PlayerCommandPreprocessEvent) {
        val message = evt.message.lowercase(Locale.getDefault())
        if (message.startsWith("/help")) {
            val args = message.split(" ")
            var category: String? = null
            if (args.size > 1) {
                category = args[1]
            }
            var messages = config.getStringList("help")
            if (category != null) {
                messages = config.getStringList(category) ?: messages
            }
            evt.player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.joinToString("\n")))
            evt.isCancelled = true
        }
    }
}
