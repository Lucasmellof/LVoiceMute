package wtf.lucasmellof.voicemute.commands

import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import net.gliby.voicechat.VoiceChat
import org.bukkit.Bukkit
import org.bukkit.Color
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
            .setColor(Color.RED.asRGB())
            .setTitle(WebhookEmbed.EmbedTitle("**${mute.muted.player.name}** foi mutado por voz!",null))
            .setThumbnailUrl("https://minotar.net/avatar/${target.uniqueId}/128")
            .setDescription(
                "\n **Servidor:** " + getPlugin().configCache.serverName + "\n**Autor:** ${sender.name}\n**Razão:** ${mute.reason}"
            )
        getPlugin().webhook.sendWebhook(embed)
        VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.add(target.uniqueId)
        target.sendTitle("§aVocê foi mutado por voz", "§3Um membro da equipe te mutou", 10, 40, 10)
    }
}
