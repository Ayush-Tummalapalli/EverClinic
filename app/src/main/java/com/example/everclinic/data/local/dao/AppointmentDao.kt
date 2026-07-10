package com.example.everclinic.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everclinic.data.local.entities.AppointmentEntity
import com.example.everclinic.data.local.relations.AppointmentDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: AppointmentEntity)

    @Delete
    suspend fun delete(appointment: AppointmentEntity)

    @Query("SELECT COUNT(*) FROM appointments")
    fun getAppointmentsCount(): Flow<Int>

    @Query("""
        SELECT
            a.*,
            p.name AS patientName,
            d.name AS doctorName
        FROM appointments AS a
        JOIN patients AS p ON a.patientId = p.id
        JOIN doctors AS d ON a.doctorId = d.id
    """)
    fun getAllAppointments(): Flow<List<AppointmentDetails>>

    @Query("""
        SELECT
            a.*,
            p.name AS patientName,
            d.name AS doctorName
        FROM appointments AS a
        JOIN patients AS p ON a.patientId = p.id
        JOIN doctors AS d ON a.doctorId = d.id
        WHERE a.id = :appointmentId
    """)
    fun getAppointmentById(appointmentId: String): Flow<AppointmentDetails?>

    @Query("""
        SELECT
            a.*,
            p.name AS patientName,
            d.name AS doctorName
        FROM appointments AS a
        JOIN patients AS p ON a.patientId = p.id
        JOIN doctors AS d ON a.doctorId = d.id
        WHERE a.date = :date
    """)
    fun getAppointmentsByDate(date: String): Flow<List<AppointmentDetails>>

    @Query("""
        SELECT 
            a.*,
            p.name as patientName, 
            d.name as doctorName
        FROM appointments AS a
        JOIN patients AS p ON a.patientId = p.id
        JOIN doctors AS d ON a.doctorId = d.id
        WHERE p.name LIKE '%' || :query || '%' 
        OR d.name LIKE '%' || :query || '%' 
        OR a.reason LIKE '%' || :query || '%'
    """)
    fun searchAppointments(query: String): Flow<List<AppointmentDetails>>
}
