package com.safframework.utils

/**
 * Created by Tony Shen on 2017/1/17.
 */
object Maps {

    /**
     * @param map
     * *
     * @return
     */
    fun <K, V> key(map: Map<K, V>?): K? {
        if (map != null) {
            val iterator = map.entries.iterator()
            if (iterator.hasNext()) {
                return iterator.next().key
            }
        }

        return null
    }

    /**
     * @param map
     * *
     * @return
     */
    fun <K, V> value(map: Map<K, V>?): V? {
        if (map != null) {
            val iterator = map.entries.iterator()
            if (iterator.hasNext()) {
                return iterator.next().value
            }
        }

        return null
    }
}