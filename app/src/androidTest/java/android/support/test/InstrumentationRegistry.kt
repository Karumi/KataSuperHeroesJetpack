package android.support.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider

/**
 * This solves the issue with mockito looking for InstrumentationRegistry with reflection.
 * Hopefully this class won't be required with a mockito update.
 *
 * Report: https://github.com/mockito/mockito/issues/1472
 * Solution: https://github.com/mockito/mockito/pull/1583
 */
class InstrumentationRegistry {
    companion object {
        @JvmStatic
        fun getTargetContext(): Context? {
            return ApplicationProvider.getApplicationContext()
        }
    }
}