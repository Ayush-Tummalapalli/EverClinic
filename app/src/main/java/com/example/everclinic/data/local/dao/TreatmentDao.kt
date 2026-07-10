package com.example.everclinic.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everclinic.data.local.entities.TreatmentEntity
import com.example.everclinic.data.local.relations.TreatmentDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface TreatmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(treatment: TreatmentEntity)

    @Delete
    suspend fun delete(treatment: TreatmentEntity)

    @Query("SELECT COUNT(*) FROM treatments")
    fun getTreatmentsCount(): Flow<Int>

    @Query("""
        SELECT
            t.*,
            p.name AS patientName,
            d.name AS doctorName
        FROM treatments AS t
        JOIN patients AS p ON t.patientId = p.id
        JOIN doctors AS d ON t.doctorId = d.id
    """)
    fun getAllTreatments(): Flow<List<TreatmentDetails>>

    @Query("""
        SELECT
            t.*,
            p.name AS patientName,
            d.name AS doctorName
        FROM treatments AS t
        JOIN patients AS p ON t.patientId = p.id
        JOIN doctors AS d ON t.doctorId = d.id
        WHERE t.id = :treatmentId
    """)
    fun getTreatmentById(treatmentId: String): Flow<TreatmentDetails?>

    @Query("""
        SELECT
            t.*,
            p.name AS patientName,
            d.name AS doctorName
        FROM treatments AS t
        JOIN patients AS p ON t.patientId = p.id
        JOIN doctors AS d ON t.doctorId = d.id
        WHERE t.diagnosis LIKE '%' || :query || '%'
        OR p.name LIKE '%' || :query || '%'
        OR d.name LIKE '%' || :query || '%'
    """)
    fun searchTreatments(query: String): Flow<List<TreatmentDetails>>
}
