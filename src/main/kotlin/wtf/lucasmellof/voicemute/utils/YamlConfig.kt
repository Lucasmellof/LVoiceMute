package wtf.lucasmellof.voicemute.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.logging.Logger

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class YamlConfig(private val filename: String, private val plugin: JavaPlugin) {
    private val logger: Logger = plugin.logger
    private var fileConfiguration: FileConfiguration? = null
    private var file: File? = null
    private fun init() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdir()
        }
        file = File(plugin.dataFolder, filename)
        if (!file!!.exists()) {
            try {
                file!!.parentFile.mkdirs()
                file!!.createNewFile()
                logger.info("$filename was created.")
            } catch (e: IOException) {
                logger.warning("Couldn't create file $filename")
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file)
        logger.info("$filename loaded!")
    }

    fun getConfig(): FileConfiguration {
        return fileConfiguration!!
    }

    fun save() {
        try {
            fileConfiguration!!.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    init {
        init()
    }
}
