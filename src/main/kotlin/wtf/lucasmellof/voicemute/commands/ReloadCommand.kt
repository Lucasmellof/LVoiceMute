package wtf.lucasmellof.voicemute.commands

import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import wtf.lucasmellof.voicemute.LVoiceMute
import wtf.lucasmellof.voicemute.exception.PluginException
import wtf.lucasmellof.voicemute.mute.Mute
import wtf.lucasmellof.voicemute.user.User

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class ReloadCommand(plugin: LVoiceMute) : CommandBase(plugin, "voicereload", "lvoicemute.reload") {
    @Throws(PluginException::class)
    override fun execute(sender: CommandSender, args: Array<String>) {
        if(sender !is Player) return
        getPlugin().configCache.loadCache(true)
        getPlugin().webhook.reload()
        sender.sendTitle("§aConfig recarregada!", "§3Aguarde alguns segundos para mexer no plugin", 10, 40, 10)
    }
}