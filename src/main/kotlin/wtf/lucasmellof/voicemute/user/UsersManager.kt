package wtf.lucasmellof.voicemute.user

import org.bukkit.Bukkit
import wtf.lucasmellof.voicemute.LVoiceMute
import wtf.lucasmellof.voicemute.utils.ConcurrentCache
import java.util.*

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class UsersManager(private val plugin: LVoiceMute) : ConcurrentCache<UUID, User>() {
    override operator fun get(uuid: UUID): User {
        if (containsKey(uuid)) {
            return super.get(uuid)!!
        }
        val user = User(plugin, Bukkit.getPlayer(uuid))
        put(uuid, user)
        return user
    }

}