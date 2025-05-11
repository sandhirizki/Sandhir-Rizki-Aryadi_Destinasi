package com.example.destinasi.database

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
}