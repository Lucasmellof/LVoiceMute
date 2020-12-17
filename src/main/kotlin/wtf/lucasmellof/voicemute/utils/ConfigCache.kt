package wtf.lucasmellof.voicemute.utils

import wtf.lucasmellof.voicemute.LVoiceMute

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class ConfigCache(private val plugin: LVoiceMute) {
    lateinit var config: ConfigAPI
    var webhook = ""
    var serverName = ""
    fun load() {
        config = ConfigAPI(plugin, "config.yml", true, header = true)
        loadCache(false)
        config.save()
    }

    fun loadCache(reload: Boolean) {
        if (reload) config.reload()
        webhook = config.getString("webhookURL", "")
        serverName = config.getString("serverName", "")
    }
}