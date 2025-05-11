package com.example.destinasi.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.destinasi.database.DestinasiDatabase
import com.example.destinasi.viewmodel.DetailViewModel
import com.example.destinasi.viewmodel.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = DestinasiDatabase.getInstance(context)
        val settingsDataStore = SettingsDataStore(context)

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db.destinasiDao, settingsDataStore) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(db.destinasiDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}
