package com.example.destinasi.ui.theme.screen

import androidx.lifecycle.ViewModel
import com.example.destinasi.model.Destinasi

class MainViewModel : ViewModel() {
    val destinasiList = listOf(
        Destinasi(1, "Gunung Bromo", "2025-07-15", "Rp 500.000"),
        Destinasi(2, "Pantai Kuta", "2025-08-20", "Rp 1.200.000"),
        Destinasi(3, "Candi Borobudur", "2025-09-10", "Rp 300.000")

    )
}