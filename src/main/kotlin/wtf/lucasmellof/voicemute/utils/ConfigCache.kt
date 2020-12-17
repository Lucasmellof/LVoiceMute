package wtf.lucasmellof.voicemute.utils

import wtf.lucasmellof.voicemute.LVoiceMute

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class ConfigCache(private val plugin: LVoiceMute) {
    lateinit var config: YamlConfig
    var webhook = ""
    var serverName = ""
    fun load() {
        config = YamlConfig("config.yml", plugin)
    }
    fun loadCache() {
        webhook = config.getConfig().getString("webookURL", "")
        serverName = config.getConfig().getString("serverName", "")
    }
}