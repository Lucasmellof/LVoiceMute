package wtf.lucasmellof.voicemute.commands

import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import wtf.lucasmellof.voicemute.LVoiceMute
import wtf.lucasmellof.voicemute.exception.PluginException
import wtf.lucasmellof.voicemute.mute.Mute
import wtf.lucasmellof.voicemute.user.User
import java.util.*

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class VoiceMuteCommand(plugin: LVoiceMute) : CommandBase(plugin, "voicemute", "lvoicemute.mute") {
    @Throws(PluginException::class)
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size < 2) {
            throw PluginException("§e ! §fUse: /voicemute <player> <razão>")
        }
        val target = Bukkit.getPlayer(args[0]) ?: throw PluginException("§c ! §fEsse jogador não está online!")
        val user: User = getPlugin().getUsers()!![target.uniqueId]
        if (user.mute != null) {
            throw PluginException("§c ! Esse jogador já está mutado!")
        }

        val mute = Mute(user, args.copyOfRange(1, args.size).joinToString(" "), System.currentTimeMillis(), sender.name)
        mute.save()
        sender.sendMessage("§a ! §f${mute.muted.player.name} foi mutado por voz")
        val embed = WebhookEmbedBuilder()
            .setDescription(
                "**${mute.muted.player.name}** foi temporariamente mutado por voz!\n **Servidor:** " + getPlugin().configCache.serverName + "\n**Autor:** ${sender.name}\n\n**Razão:** ${mute.reason}"
            )
        getPlugin().webhook.sendWebhook(embed)
    }
}
