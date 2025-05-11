package com.example.destinasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.destinasi.database.DestinasiDao
import com.example.destinasi.model.Destinasi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(private val destinasiDao: DestinasiDao) : ViewModel() {

    fun getDestinasiById(id: Long): Flow<Destinasi?> {
        return destinasiDao.getDestinasiById(id)
    }

    fun insertDestinasi(destinasi: Destinasi) {
        viewModelScope.launch {
            destinasiDao.insert(destinasi)
        }
    }

    fun updateDestinasi(destinasi: Destinasi) {
        viewModelScope.launch {
            destinasiDao.update(destinasi)
        }
    }

    fun deleteDestinasi(destinasi: Destinasi) {
        viewModelScope.launch {
            destinasiDao.delete(destinasi)
        }
    }
}