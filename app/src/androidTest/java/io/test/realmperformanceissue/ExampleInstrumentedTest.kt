package io.test.realmperformanceissue

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.kotlin.where
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@RunWith(AndroidJUnit4::class)
@ExperimentalTime
class ExampleInstrumentedTest {

    @Test
    fun testPerformance() {
        // Init and reset realm
        Realm.init(InstrumentationRegistry.getInstrumentation().targetContext)
        RealmConfiguration.Builder().build().let { config ->
            Realm.deleteRealm(config)
            Realm.setDefaultConfiguration(config)
        }

        // Populate db with 150k random rows
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                repeat(150000) { realm.insertOrUpdate(Model0()) }
            }
        }

        // Query db
        listOf(25, 50, 100, 200, 400, 600, 800, 1000).forEach { queryCount ->
                (0..10) // We make several queries in order to calculate an average
                    .map {
                        measureTime {
                            Realm.getDefaultInstance().use { realm ->
                                repeat(queryCount) {
                                    // we retrieve the first row for a simple query, and copy it from realm
                                    realm.where<Model0>()
                                        .equalTo(Model0::indexedValue.name, 1.toInt())
                                        .equalTo(Model0::otherValue.name, 1.toInt())
                                        .findFirst()
                                        ?.let(realm::copyFromRealm)
                                }
                            }
                        }
                    }
                    .map { it.inMilliseconds }
                    .average()
                    .toInt()
                    .apply {
                        Log.i("RealmInvestigation", "For $queryCount queries: ${this}ms (average)")
                    }
            }
    }
}

@RealmClass
open class Model0 : RealmModel {

    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    @Index
    var indexedValue = Random().nextInt(10)
    var otherValue = Random().nextInt(10)

    var field0: String = UUID.randomUUID().toString()
    var field1: String = UUID.randomUUID().toString()
    var field2: String = UUID.randomUUID().toString()
    var field3: String = UUID.randomUUID().toString()
    var field4: String = UUID.randomUUID().toString()
    var field5: String = UUID.randomUUID().toString()
    var field6: String = UUID.randomUUID().toString()
    var field7: String = UUID.randomUUID().toString()
    var field8: String = UUID.randomUUID().toString()
    var field9: String = UUID.randomUUID().toString()
    var field10: String = UUID.randomUUID().toString()
    var field11: String = UUID.randomUUID().toString()
    var field12: String = UUID.randomUUID().toString()
    var field13: String = UUID.randomUUID().toString()
    var field14: String = UUID.randomUUID().toString()
    var field15: String = UUID.randomUUID().toString()

    var date0: Date = Date(Random().nextLong())
    var date1: Date = Date(Random().nextLong())
    var date2: Date = Date(Random().nextLong())
    var date3: Date = Date(Random().nextLong())
    var date4: Date = Date(Random().nextLong())
    var date5: Date = Date(Random().nextLong())
    var date6: Date = Date(Random().nextLong())
    var date7: Date = Date(Random().nextLong())
    var date8: Date = Date(Random().nextLong())
    var date9: Date = Date(Random().nextLong())
}