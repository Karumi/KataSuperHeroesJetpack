package com.karumi.jetpack.superheroes.data.repository.room

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.jetpack.superheroes.common.Migrations
import com.karumi.jetpack.superheroes.common.SuperHeroesDatabase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MigrationTest {
    companion object {
        private const val TEST_DB = "migration-test"
        private const val SUPER_HERO_ID = "IronMan"
        private const val SUPER_HERO_NAME = "Iron Man"
        private val SUPER_HERO_URL: String? = null
        private const val SUPER_HERO_IS_AVENGER = true
        private const val SUPER_HER_DESCRIPTION = "Iron Man is a super hero"
    }

    @Rule
    @JvmField
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        SuperHeroesDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate1To2() = withDb(
        fromVersion = 1,
        toVersion = 2,
        given = { insertSuperHeroInVersion1() },
        then = { assertSuperHeroExistsInVersion2() }
    )

    private fun withDb(
        fromVersion: Int,
        toVersion: Int,
        given: SupportSQLiteDatabase.() -> Unit,
        then: SupportSQLiteDatabase.() -> Unit
    ) {
        val db = helper.createDatabase(TEST_DB, fromVersion)
        given(db)
        helper.runMigrationsAndValidate(TEST_DB, toVersion, true, *Migrations.all)
        then(db)
        helper.closeWhenFinished(db)
    }

    private fun SupportSQLiteDatabase.assertSuperHeroExistsInVersion2() {
        val cursor = query("SELECT * FROM superheroes")
        cursor.moveToFirst()
        assertEquals(SUPER_HERO_ID, cursor.getString(0))
        assertEquals(SUPER_HERO_NAME, cursor.getString(1))
        assertEquals(SUPER_HERO_URL, cursor.getString(2))
        assertEquals(SUPER_HERO_IS_AVENGER.toInt(), cursor.getInt(3))
        assertEquals(SUPER_HER_DESCRIPTION, cursor.getString(4))
    }

    private fun SupportSQLiteDatabase.insertSuperHeroInVersion1() {
        execSQL(
            """
                INSERT INTO superheroes VALUES(
                    "$SUPER_HERO_ID",
                    "$SUPER_HERO_NAME",
                    $SUPER_HERO_URL,
                    ${SUPER_HERO_IS_AVENGER.toInt()},
                    "$SUPER_HER_DESCRIPTION"
                )"""
        )
    }
}

private fun Boolean.toInt() = if (this) 1 else 0
