package wtf.lucasmellof.voicemute.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import wtf.lucasmellof.voicemute.LVoiceMute
import wtf.lucasmellof.voicemute.exception.NoPermissionException
import wtf.lucasmellof.voicemute.exception.PluginException

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
abstract class CommandBase : CommandExecutor {

    private var plugin: LVoiceMute? = null
    private var permission: String? = null

    constructor (plugin: LVoiceMute, name: String, permission: String) {
        this.plugin = plugin
        this.permission = permission
        plugin.getCommand(name).executor = this
    }

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        try {
            if (permission != null && !commandSender.hasPermission(permission)) {
                throw NoPermissionException()
            }
            execute(commandSender, strings)
        } catch (e: PluginException) {
            commandSender.sendMessage(e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    @Throws(PluginException::class)
    abstract fun execute(sender: CommandSender, args: Array<String>)

    fun getPlugin(): LVoiceMute {
        return plugin!!
    }
}