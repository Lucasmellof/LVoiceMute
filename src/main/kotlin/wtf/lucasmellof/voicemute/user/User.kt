package wtf.lucasmellof.voicemute.user

import org.bukkit.entity.Player
import wtf.lucasmellof.voicemute.LVoiceMute
import wtf.lucasmellof.voicemute.mute.Mute
import wtf.lucasmellof.voicemute.utils.YamlConfig

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class User(val plugin: LVoiceMute, val player: Player) {
    var mute: Mute? = null
    val file: YamlConfig = YamlConfig("players/" + player.uniqueId + ".yml", plugin)
    private fun load() {
        val muteSection = file.getConfig().getConfigurationSection("mute")
        if (muteSection == null) {
            file.getConfig().createSection("mute")
            return
        }
        mute = Mute(
            this,
            muteSection.getString("reason"),
            muteSection.getLong("duration"),
            muteSection.getString("author")
        )
    }


    init {
        load()
    }
}
