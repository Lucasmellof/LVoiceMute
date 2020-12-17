package wtf.lucasmellof.voicemute.utils

import com.google.common.io.Files.createParentDirs
import org.apache.commons.lang.Validate
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import java.io.*


/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 03/06/2020
 */
class Utf8YamlConfiguration : YamlConfiguration() {
    @Throws(IOException::class)
    override fun save(file: File?) {
        Validate.notNull(file, "File cannot be null")
        createParentDirs(file)
        val data = saveToString()
        val writer: Writer = OutputStreamWriter(FileOutputStream(file), Charsets.UTF_8)
        writer.use { writer ->
            writer.write(data)
        }
    }

    @Throws(FileNotFoundException::class, IOException::class, InvalidConfigurationException::class)
    override fun load(file: File?) {
        Validate.notNull(file, "File cannot be null")
        this.load(InputStreamReader(FileInputStream(file), Charsets.UTF_8))
    }
}