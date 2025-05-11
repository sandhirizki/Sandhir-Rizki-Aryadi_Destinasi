package com.example.destinasi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.destinasi.model.Destinasi
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinasiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(destinasi: Destinasi)

    @Update
    suspend fun update(destinasi: Destinasi)

    @Delete
    suspend fun delete(destinasi: Destinasi)

    @Query("SELECT * FROM destinasi ORDER BY tanggalKeberangkatan DESC")
    fun getAllDestinasi(): Flow<List<Destinasi>>

    @Query("SELECT * FROM destinasi WHERE id = :id")
    fun getDestinasiById(id: Long): Flow<Destinasi?>

    @Query("SELECT * FROM destinasi WHERE isDeleted = 0")
    fun getDestinasiList(): Flow<List<Destinasi>>

    @Query("SELECT * FROM destinasi WHERE isDeleted = 1")
    fun getDeletedDestinasi(): Flow<List<Destinasi>>

    @Update
    suspend fun updateDestinasi(destinasi: Destinasi)
}