
/**
 * This file contains the unit tests for the PatientDao.
 */
package com.example.everclinic.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.everclinic.data.local.EverclinicDatabase
import com.example.everclinic.data.local.entities.PatientEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PatientDaoTest {

    private lateinit var database: EverclinicDatabase
    private lateinit var patientDao: PatientDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EverclinicDatabase::class.java
        ).build()
        patientDao = database.patientDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetPatient() = runBlocking {
        val patient = PatientEntity(
            id = "1", name = "John Doe", age = 30, gender = "Male", phone = "1234567890",
            address = "123 Main St", emergencyContactName = "Jane Doe", emergencyContactPhone = "0987654321",
            lastVisit = "2023-10-27T10:00:00.000Z", condition = "Fever", bloodGroup = "O+",
            height = 180f, weight = 75f, bmi = 23.1f, chronicConditions = "[]", allergies = "[]",
            currentMedications = "[]", pastSurgeries = "[]", smoking = false, alcohol = false,
            activityLevel = "Moderate", insuranceProvider = "ABC Insurance", policyNumber = "12345",
            validity = "2025-12-31T23:59:59.999Z"
        )
        patientDao.insert(patient)

        val allPatients = patientDao.getAll().first()
        assertEquals(allPatients[0], patient)
    }
}
