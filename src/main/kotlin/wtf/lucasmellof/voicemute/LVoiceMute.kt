package wtf.lucasmellof.voicemute

import org.bukkit.plugin.java.JavaPlugin
import wtf.lucasmellof.voicemute.commands.CheckMuteCommand
import wtf.lucasmellof.voicemute.commands.ReloadCommand
import wtf.lucasmellof.voicemute.commands.UnVoiceMuteCommand
import wtf.lucasmellof.voicemute.commands.VoiceMuteCommand
import wtf.lucasmellof.voicemute.listeners.PlayerListener
import wtf.lucasmellof.voicemute.user.UsersManager
import wtf.lucasmellof.voicemute.utils.ConfigCache
import wtf.lucasmellof.voicemute.utils.WebhookBuilder

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class LVoiceMute : JavaPlugin() {
    private var users: UsersManager? = null
    lateinit var webhook: WebhookBuilder
    lateinit var configCache: ConfigCache

    override fun onLoad() {
        users = UsersManager(this)
    }

    override fun onEnable() {
        loadModules()
    }

    private fun loadModules() {
        configCache = ConfigCache(this)
        configCache.load()
        UnVoiceMuteCommand(this)
        VoiceMuteCommand(this)
        CheckMuteCommand(this)
        ReloadCommand(this)
        webhook = WebhookBuilder(this)
        webhook.load()
        PlayerListener(this)
    }

    fun getUsers(): UsersManager? {
        return users
    }
}