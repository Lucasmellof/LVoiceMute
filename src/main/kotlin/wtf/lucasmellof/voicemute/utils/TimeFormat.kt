package wtf.lucasmellof.voicemute.utils

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
enum class TimeFormat(private val time: Long) {
    s(1000L), m(60000L), h(3600000L), d(86400000L), w(604800000L), M(2592000000L), y(31104000000L);

    fun getTime(): Long {
        return time
    }

    companion object {
        fun getFormat(str: String): TimeFormat? {
            var str = str
            str = str.trim { it <= ' ' }
            for (unit in values()) {
                if (str.matches(Regex("\\d+$unit"))) {
                    return unit
                }
            }
            return null
        }
    }

}
