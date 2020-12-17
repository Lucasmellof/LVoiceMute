package wtf.lucasmellof.voicemute.commands

import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import org.bukkit.Bukkit
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
            .setDescription("**${mute.muted.player.name}** foi desmutado mutado por voz!\n **Servidor:** " + getPlugin().server.serverName + "\n**Autor:** ${sender.name}")
        getPlugin().webhook.sendWebhook(embed)
        target.sendTitle("§aVocê foi desmutado", "§3Um membro da equipe te desmutou", 10, 40, 10)
    }
}