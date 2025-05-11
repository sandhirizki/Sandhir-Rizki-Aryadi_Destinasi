package com.example.destinasi.ui.theme.screen

import kotlinx.coroutines.flow.collectLatest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.destinasi.R
import com.example.destinasi.model.Destinasi
import com.example.destinasi.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    navController: NavHostController,
    viewModel: DetailViewModel,
    destinasiId: Long?
) {
    val context = LocalContext.current
    var currentDestinasi by remember { mutableStateOf<Destinasi?>(null) }

    var namaObjekWisata by remember { mutableStateOf("") }
    var tanggalKeberangkatan by remember { mutableStateOf("") }
    var estimasiBiaya by remember { mutableStateOf("") }

    var showDeleteDialog by remember { mutableStateOf(false) }

    val isEditMode = destinasiId != null

    LaunchedEffect(key1 = destinasiId) {
        if (isEditMode) {
            viewModel.getDestinasiById(destinasiId!!).collectLatest { destinasi ->
                currentDestinasi = destinasi
                destinasi?.let {
                    namaObjekWisata = it.namaObjekWisata
                    tanggalKeberangkatan = it.tanggalKeberangkatan
                    estimasiBiaya = it.estimasiBiaya
                }
            }
        } else {
            namaObjekWisata = ""
            tanggalKeberangkatan = ""
            estimasiBiaya = ""
            currentDestinasi = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) stringResource(R.string.ubah_destinasi)
                        else stringResource(R.string.tambah_destinasi_baru)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.kembali))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (namaObjekWisata.isBlank() || tanggalKeberangkatan.isBlank() || estimasiBiaya.isBlank()) {
                            Toast.makeText(context, context.getString(R.string.semua_field_harus_diisi), Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        val destinasiToSave = Destinasi(
                            id = currentDestinasi?.id ?: 0L,
                            namaObjekWisata = namaObjekWisata.trim(),
                            tanggalKeberangkatan = tanggalKeberangkatan.trim(),
                            estimasiBiaya = estimasiBiaya.trim()
                        )
                        if (isEditMode) {
                            viewModel.updateDestinasi(destinasiToSave)
                        } else {
                            viewModel.insertDestinasi(destinasiToSave)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.simpan))
                    }
                    if (isEditMode && currentDestinasi != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.hapus))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        FormDestinasi(
            namaObjekWisata = namaObjekWisata,
            onNamaObjekWisataChange = { namaObjekWisata = it },
            tanggalKeberangkatan = tanggalKeberangkatan,
            onTanggalKeberangkatanChange = { tanggalKeberangkatan = it },
            estimasiBiaya = estimasiBiaya,
            onEstimasiBiayaChange = { estimasiBiaya = it },
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        )

        if (showDeleteDialog && currentDestinasi != null) {
            DisplayAlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                onConfirmation = {
                    viewModel.deleteDestinasi(currentDestinasi!!)
                    showDeleteDialog = false
                    navController.popBackStack()
                },
                dialogTitle = stringResource(R.string.konfirmasi_hapus),
                dialogText = stringResource(R.string.apakah_yakin_hapus_destinasi, currentDestinasi!!.namaObjekWisata)
            )
        }
    }
}

@Composable
fun FormDestinasi(
    namaObjekWisata: String,
    onNamaObjekWisataChange: (String) -> Unit,
    tanggalKeberangkatan: String,
    onTanggalKeberangkatanChange: (String) -> Unit,
    estimasiBiaya: String,
    onEstimasiBiayaChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaObjekWisata,
            onValueChange = onNamaObjekWisataChange,
            label = { Text(stringResource(R.string.nama_objek_wisata)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = tanggalKeberangkatan,
            onValueChange = onTanggalKeberangkatanChange,
            label = { Text(stringResource(R.string.tanggal_keberangkatan_yyyy_mm_dd)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = estimasiBiaya,
            onValueChange = onEstimasiBiayaChange,
            label = { Text(stringResource(R.string.estimasi_biaya_rp)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
    }
}