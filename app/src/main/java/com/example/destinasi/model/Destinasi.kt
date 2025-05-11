package com.example.destinasi.model

data class Destinasi(
    val id: Long = 0L, // Default value untuk Room
    val namaObjekWisata: String,
    val tanggalKeberangkatan: String,
    val estimasiBiaya: String // Atau Double
)

