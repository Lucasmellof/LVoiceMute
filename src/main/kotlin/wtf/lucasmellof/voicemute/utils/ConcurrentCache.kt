package wtf.lucasmellof.voicemute.utils

import java.util.concurrent.ConcurrentHashMap

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
open class ConcurrentCache<K, T> {
    private val cache: MutableMap<K, T> = ConcurrentHashMap()
    fun put(key: K, `object`: T) {
        cache[key] = `object`
    }

    fun remove(key: K) {
        cache.remove(key)
    }

    open operator fun get(key: K): T? {
        return cache[key]
    }

    fun containsKey(key: K): Boolean {
        return cache.containsKey(key)
    }
}