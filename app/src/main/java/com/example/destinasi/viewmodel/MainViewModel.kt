package com.example.destinasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.destinasi.database.DestinasiDao
import com.example.destinasi.model.Destinasi
import com.example.destinasi.util.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val destinasiDao: DestinasiDao,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val destinasiList: StateFlow<List<Destinasi>> = destinasiDao.getAllDestinasi()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val layoutPreference: StateFlow<Boolean> = settingsDataStore.preferenceFlow
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    fun saveLayoutPreference(isListView: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveLayoutPreference(isListView)
        }
    }

}