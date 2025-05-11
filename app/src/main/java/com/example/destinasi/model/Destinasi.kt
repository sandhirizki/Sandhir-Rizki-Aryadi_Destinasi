package com.example.destinasi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinasi")
data class Destinasi(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaObjekWisata: String,
    val tanggalKeberangkatan: String,
    val estimasiBiaya: String
)