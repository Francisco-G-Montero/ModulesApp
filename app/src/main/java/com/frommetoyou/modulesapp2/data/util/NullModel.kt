package com.frommetoyou.modulesapp2.data.util

/**
 *
 * Class for return nothing
 */
class NullModel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}