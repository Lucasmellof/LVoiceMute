package wtf.lucasmellof.voicemute.listeners

import net.gliby.voicechat.VoiceChat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable
import wtf.lucasmellof.voicemute.LVoiceMute

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class PlayerListener(private val plugin: LVoiceMute) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        // Load user cache
        val user = plugin.getUsers()?.get(event.player.uniqueId)
        if (user != null ) {
            VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.add(event.player.uniqueId)
            object : BukkitRunnable() {
                override fun run() {
                    event.player.sendTitle(
                        "§cVocê está mutado por voz",
                        "§3Você só vai poder falar quando um membro da equipe te desmutar",
                        10,
                        5 * 20,
                        10
                    )
                }

            }.runTaskLater(plugin, 5 * 20)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        // Remove from cache when user logs out
        plugin.getUsers()?.remove(event.player.uniqueId)
        if (VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.contains(event.player.uniqueId)) {
            VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.remove(event.player.uniqueId)
        }
    }

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}