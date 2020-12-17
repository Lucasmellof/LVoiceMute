package wtf.lucasmellof.voicemute.utils

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 24/05/2020
 */
import org.bukkit.*
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*


class ConfigAPI {
    var theFile: File
    var config: FileConfiguration
    var newDefaultValueToSave: Boolean

    constructor(plugin: Plugin) {
        newDefaultValueToSave = false
        theFile =
            File("plugins/" + plugin.description.name.replace(" ", "_") + "/config.yml")
        config = Utf8YamlConfiguration()
        config.load(theFile)
    }

    constructor(plugin: Plugin, configName: String, copyDefaults: Boolean, header: Boolean) {
        newDefaultValueToSave = false
        theFile = File(
            "plugins/" + plugin.description.name.replace(" ", "_") + "/" + configName
        )
        if (!theFile.exists()) {
            val theFileParentDir = theFile.parentFile
            theFileParentDir.mkdirs()
            if (copyDefaults) {
                copyAsset(configName, theFileParentDir, plugin)
            }
        }
        config = Utf8YamlConfiguration()
        config.load(theFile)
        if (header) {
            config.options().header(
                "--------------------------------------------------------\n              Lucasmellof's config\n\n Plugin: " + plugin.name
                    .replace("Lucasmellof", "") + "\n Author: " + (if (plugin.description
                        .authors.size > 0
                ) plugin.description.authors[0] else "Desconhecido").toString() + "\n\n N\u00e3o edite esse arquivo caso voc\u00ea n\u00e3o saiba usa-lo!\n-------------------------------------------------------"
            )
        }
    }

    constructor(plugin: Plugin, configName: String) : this(plugin, configName, true, false)
    constructor(plugin: Plugin, configName: String, copyDefaults: Boolean) : this(
        plugin,
        configName,
        copyDefaults,
        true
    )

    constructor(theFile: File) {
        newDefaultValueToSave = false
        this.theFile = theFile
        config = YamlConfiguration.loadConfiguration(this.theFile) as FileConfiguration
    }

    constructor(theFile: File, config: FileConfiguration) {
        newDefaultValueToSave = false
        this.theFile = theFile
        this.config = config
    }

    constructor(path: String) {
        newDefaultValueToSave = false
        theFile = File(path)
        config = YamlConfiguration.loadConfiguration(theFile) as FileConfiguration
    }

    val configuration: FileConfiguration
        get() = config

    fun store(path: String, value: Any?) {
        config.set(path, value)
    }

    fun setValue(path: String, value: Any) {
        when (value) {
            null -> {
                this.store(path, value)
                this.store(path + "_extra", null)
            }
            is Inventory -> {
                for (i in 0 until value.size) {
                    this.setValue("$path.$i", value.getItem(i))
                }
            }
            is Date -> {
                store(path, value.time.toString())
            }
            is Long -> {
                store(path, value)
            }
            is UUID -> {
                store(path, value.toString())
            }
            is Sound -> {
                store(path, value.toString())
            }
            is ItemStack -> {
                store(path, ItemStack(value))
            }
            is Location -> {
                this.setValue("$path.x", value.x)
                this.setValue("$path.y", value.y)
                this.setValue("$path.z", value.z)
                this.setValue("$path.pitch", value.pitch)
                this.setValue("$path.yaw", value.yaw)
                this.setValue("$path.worldName", value.world.name)
            }
            is Chunk -> {
                this.setValue("$path.x", value.x)
                this.setValue("$path.z", value.z)
                this.setValue("$path.worldName", value.world.name)
            }
            is World -> {
                store(path, value.name)
            }
            else -> {
                store(path, value)
            }
        }
    }

    fun save() {
        try {
            config.save(theFile)
        } catch (ex: IOException) {
        }
    }

    fun saveIfNewDefaults() {
        if (newDefaultValueToSave) {
            this.save()
            newDefaultValueToSave = false
        }
    }

    fun save(file: File) {
        try {
            config.save(file)
        } catch (ex: IOException) {
        }
    }

    fun getOrSetDefaultValue(path: String, def: List<String>): List<String> {
        return this.getOrSetDefaultValue(path, def as Any) as List<String>
    }

    fun getOrSetDefaultValue(path: String, def: Location): Location {
        return this.getOrSetDefaultValue(path, def as Any) as Location
    }

    fun getOrSetDefaultValue(path: String, def: Int): Int {
        return this.getOrSetDefaultValue(path, def as Any) as Int
    }

    fun getOrSetDefaultValue(path: String, def: Long): Long {
        return this.getOrSetDefaultValue(path, def as Any) as Long
    }

    fun getOrSetDefaultValue(path: String, def: Double): Double {
        return this.getOrSetDefaultValue(path, def as Any) as Double
    }

    fun getOrSetDefaultValue(path: String, def: Float): Float {
        return this.getOrSetDefaultValue(path, def as Any) as Float
    }

    fun getOrSetDefaultValue(path: String, def: String): String {
        return this.getOrSetDefaultValue(path, def as Any) as String
    }

    fun getOrSetDefaultValue(path: String, def: Boolean): Boolean {
        return this.getOrSetDefaultValue(path, def as Any) as Boolean
    }

    fun getOrSetDefaultValue(path: String, value: Any): Any {
        if (!this.contains(path)) {
            this.setValue(path, value)
            return value
        }
        var `object` = this.getValue(path)
        if (`object`.javaClass != value.javaClass) {
            `object` = value
            this.setValue(path, value)
        }
        if (!newDefaultValueToSave) {
            newDefaultValueToSave = true
        }
        return `object`
    }

    fun getOrSave(path: String, value: Any): Any {
        if (!this.contains(path)) {
            this.setValue(path, value)
            this.save()
            return value
        }
        return this.getValue(path)
    }

    fun setDefaultValue(path: String, value: Any) {
        if (!this.contains(path)) {
            this.setValue(path, value)
        }
    }

    operator fun contains(path: String): Boolean {
        return config.contains(path)
    }

    fun getValue(path: String): Any {
        return config.get(path)
    }

    fun getItem(path: String?): ItemStack? {
        return config.getItemStack(path) ?: return null
    }

    fun getString(path: String): String? {
        return config.getString(path)
    }

    fun getString(path: String, def: String): String {
        return config.getString(path, def)
    }

    fun getInt(path: String): Int {
        return config.getInt(path)
    }

    fun getInt(path: String, def: Int): Int {
        return config.getInt(path, def)
    }

    fun getBoolean(path: String): Boolean {
        return config.getBoolean(path)
    }

    fun getBoolean(path: String, def: Boolean): Boolean {
        return config.getBoolean(path, def)
    }

    fun getStringList(path: String): List<String> {
        return config.getStringList(path)
    }

    fun getStringList(
        path: String,
        def: List<String>
    ): List<String> {
        return if (this.contains(path)) {
            config.getStringList(path)
        } else def
    }

    fun getItemList(path: String): List<ItemStack> {
        val list: MutableList<ItemStack> = ArrayList<ItemStack>()
        for (key in getKeys(path)) {
            if (!key.endsWith("_extra")) {
                getItem("$path.$key")?.let { list.add(it) }
            }
        }
        return list
    }

    fun getIntList(path: String): List<Int> {
        return config.getIntegerList(path)
    }

    fun createFile() {
        try {
            theFile.createNewFile()
        } catch (ex: IOException) {
        }
    }

    fun getFloat(path: String): Float {
        return java.lang.Float.valueOf(this.getValue(path).toString())
    }

    fun getLong(path: String): Long {
        return java.lang.Long.valueOf(this.getValue(path).toString())
    }

    fun getLong(path: String, def: Long): Long {
        return if (!this.contains(path)) {
            def
        } else this.getLong(path)
    }

    fun getSound(path: String): Sound {
        return Sound.valueOf(this.getString(path).toString())
    }

    fun getDate(path: String): Date? {
        return if (this.contains(path)) {
            Date(this.getLong(path))
        } else null
    }

    fun getChunk(path: String): Chunk {
        return Bukkit.getWorld(this.getString("$path.worldName"))
            .getChunkAt(this.getInt("$path.x"), this.getInt("$path.z"))
    }

    fun getUUID(path: String): UUID? {
        return if (this.contains(path)) {
            UUID.fromString(this.getString(path))
        } else null
    }

    fun getUUID(path: String, def: UUID): UUID {
        return UUID.fromString(this.getString(path, def.toString()))
    }

    fun getWorld(path: String): World {
        return Bukkit.getWorld(this.getString(path))
    }

    fun getDouble(path: String): Double {
        return config.getDouble(path)
    }

    fun getDouble(path: String, def: Double): Double {
        return config.getDouble(path, def)
    }

    fun getLocation(path: String): Location? {
        return Location(
            Bukkit.getWorld(this.getString("$path.worldName")),
            this.getDouble("$path.x"),
            this.getDouble("$path.y"),
            this.getDouble("$path.z"),
            getFloat("$path.yaw"),
            getFloat("$path.pitch")
        )
    }

    fun setLocation(path: String, location: Location) {
        this.setValue("$path.x", location.x)
        this.setValue("$path.y", location.y)
        this.setValue("$path.z", location.z)
        this.setValue("$path.pitch", location.pitch)
        this.setValue("$path.yaw", location.yaw)
        this.setValue("$path.worldName", location.world.name)
    }

    @Deprecated("")
    fun setInventory(path: String, inventory: Inventory) {
        for (i in 0 until inventory.size) {
            this.setValue("$path.$i", inventory.getItem(i))
        }
    }

    fun getInventory(path: String, size: Int, title: String): Inventory {
        val inventory: Inventory = Bukkit.createInventory(null as InventoryHolder, size, title)
        for (i in 0 until size) {
            inventory.setItem(i, getItem("$path.$i"))
        }
        return inventory
    }

    val keys: Set<String>
        get() = config.getKeys(false)

    fun getKeys(path: String): Set<String> {
        return if (this.contains(path)) {
            config.getConfigurationSection(path).getKeys(false)
        } else emptySet()
    }

    fun reload() {
        config = Utf8YamlConfiguration()
        config.load(theFile)
    }

    companion object {
        fun copyAsset(assetName: String?, targetFolder: File?, plugin: Plugin): File? {
            return try {
                val file = File(targetFolder, assetName)
                if (!file.exists()) {
                    val inputStream: InputStream = plugin.getResource(assetName)
                    try {
                        Files.copy(
                            inputStream,
                            file.absoluteFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                }
                file
            } catch (e2: Exception) {
                e2.printStackTrace()
                null
            }
        }
    }
}
