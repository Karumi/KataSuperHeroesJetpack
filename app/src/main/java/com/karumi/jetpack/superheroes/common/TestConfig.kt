package com.karumi.jetpack.superheroes.common

object TestConfig {
    val runningTests by lazy {
        isRunningUITests() || isRunningRobolectricTests()
    }

    private fun isRunningRobolectricTests(): Boolean = checkIfClassIsAvailable("org.robolectric.RobolectricTestRunner")

    private fun isRunningUITests(): Boolean = checkIfClassIsAvailable("com.karumi.TestRunner")

    private fun checkIfClassIsAvailable(s: String): Boolean {
        return try {
            Class.forName(s)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}