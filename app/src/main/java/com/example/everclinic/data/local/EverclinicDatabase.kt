
/**
 * This file defines the Room database for the Everclinic application.
 * It includes the entities, DAOs, and a callback for prepopulating the database.
 */
package com.example.everclinic.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.everclinic.data.local.dao.AppointmentDao
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.dao.TreatmentDao
import com.example.everclinic.data.local.entities.AppointmentEntity
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.data.local.entities.PatientEntity
import com.example.everclinic.data.local.entities.TreatmentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PatientEntity::class,
        DoctorEntity::class,
        AppointmentEntity::class,
        TreatmentEntity::class
    ],
    version = 6, // Incremented version to force recreation
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EverclinicDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun doctorDao(): DoctorDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun treatmentDao(): TreatmentDao

    companion object {
        @Volatile
        private var INSTANCE: EverclinicDatabase? = null

        fun getDatabase(
            context: Context,
            coroutineScope: CoroutineScope
        ): EverclinicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EverclinicDatabase::class.java,
                    "everclinic_database"
                )
                    // This will now trigger, destroying old data and recreating the schema.
                    .fallbackToDestructiveMigration()
                    .addCallback(EverclinicDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class EverclinicDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch(Dispatchers.IO) {
                    // Pre-populate database here using DAOs if needed
                }
            }
        }
    }
}