package com.karumi.common

interface TimeProvider {
    val time: Long
}

class RealTimeProvider : TimeProvider {
    override val time = System.currentTimeMillis()
}

