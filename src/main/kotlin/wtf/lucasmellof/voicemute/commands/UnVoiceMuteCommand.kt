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

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class UnVoiceMuteCommand(plugin: LVoiceMute) : CommandBase(plugin, "unvoicemute", "lvoicemute.unmute") {
    @Throws(PluginException::class)
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            throw PluginException("§e ! §fUse: /unvoicemute <player>")
        }
        val target = Bukkit.getPlayer(args[0]) ?: throw PluginException("§c ! §fEsse jogador não está online!")
        val user: User = getPlugin().getUsers()!![target.uniqueId]
        val mute: Mute = user.mute ?: throw PluginException("§c ! §fEsse jogador não está mutado!")
        mute.cancel()
        sender.sendMessage("§c ! §fO jogador foi desmutado!")
        val embed = WebhookEmbedBuilder()
            .setColor(Color.GREEN.asRGB())
            .setTitle(WebhookEmbed.EmbedTitle("**${mute.muted.player.name}** foi desmutado por voz!",null))
            .setThumbnailUrl("https://minotar.net/avatar/${target.uniqueId}/128")
            .setDescription("**Servidor:** " + getPlugin().configCache.serverName + "\n**Autor:** ${sender.name}")
        getPlugin().webhook.sendWebhook(embed)
        if (VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.contains(target.uniqueId)) {
            VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.remove(target.uniqueId)
        }
        target.sendTitle("§aVocê foi desmutado", "§3Um membro da equipe te desmutou", 10, 40, 10)
    }
}