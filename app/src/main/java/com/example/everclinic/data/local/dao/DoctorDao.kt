package com.example.everclinic.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everclinic.data.local.entities.DoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: DoctorEntity)

    @Query("SELECT * FROM doctors")
    fun getAll(): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getById(id: String): Flow<DoctorEntity?>

    @Delete
    suspend fun delete(doctor: DoctorEntity)

    @Query("SELECT * FROM doctors WHERE name LIKE '%' || :query || '%' OR specialty LIKE '%' || :query || '%'")
    fun searchDoctors(query: String): Flow<List<DoctorEntity>>
}
