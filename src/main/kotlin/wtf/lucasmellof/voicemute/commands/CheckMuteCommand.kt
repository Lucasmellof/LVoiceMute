package wtf.lucasmellof.voicemute.commands

import net.gliby.voicechat.VoiceChat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import wtf.lucasmellof.voicemute.LVoiceMute
import wtf.lucasmellof.voicemute.exception.PluginException
import wtf.lucasmellof.voicemute.user.User

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class CheckMuteCommand(plugin: LVoiceMute) : CommandBase(plugin, "checkvoicemute", "lvoicemute.check") {
    @Throws(PluginException::class)
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (sender !is Player) return
        if (args.isEmpty()) {
            show(sender)
            return
        }
        if (!sender.hasPermission("lvoicemute.check.others")) show(sender)
        val target = Bukkit.getPlayer(args[0]) ?: throw PluginException("§c ! §fEsse jogador não está online!")
        val user: User = getPlugin().getUsers()!![target.uniqueId]
        if (user.mute == null) {
            if (VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.contains(target.uniqueId)) {
                VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.remove(target.uniqueId)
            }
            throw PluginException("§c ! Esse jogador não está mutado!")
        }

        sender.sendMessage("§a ! §f${target.name} está mutado!\n§fMotivo: §c${user.mute!!.reason}\n§fAutor: §b${user.mute!!.author}")
    }

    fun show(p: Player) {
        val user = getPlugin().getUsers()!![p.uniqueId]
        if (user.mute == null) {
            p.sendMessage("§a ! §fVocê não está mutado")
            if (VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.contains(p.uniqueId)) {
                VoiceChat.getServerInstance().getServerNetwork().dataManager.mutedPlayers.remove(p.uniqueId)
            }
            return
        }
        p.sendMessage("§a ! §fVocê está mutado!\n§fMotivo: §c${user.mute!!.reason}\n§fAutor: §b${user.mute!!.author}")
    }
}