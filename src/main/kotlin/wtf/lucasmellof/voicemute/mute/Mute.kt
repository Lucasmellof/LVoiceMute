package wtf.lucasmellof.voicemute.mute

import org.bukkit.configuration.file.FileConfiguration
import wtf.lucasmellof.voicemute.user.User

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class Mute(val muted: User, val reason: String, val createdAt: Long, val author: String) {


    fun save() {
        val config: FileConfiguration = muted.file.getConfig()
        config["mute.author"] = author
        config["mute.createdAt"] = createdAt
        config["mute.reason"] = reason
        muted.file.save()
        muted.mute = this
    }

    fun cancel() {
        val config: FileConfiguration = muted.file.getConfig()
        config["mute"] = null
        muted.file.save()
        muted.mute = null
    }

}