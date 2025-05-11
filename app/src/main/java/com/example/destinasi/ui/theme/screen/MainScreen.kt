package com.example.destinasi.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.destinasi.R
import com.example.destinasi.model.Destinasi
import com.example.destinasi.util.ViewModelFactory
import com.example.destinasi.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    val destinasiList by viewModel.destinasiList.collectAsState(initial = emptyList())
    val isListView by viewModel.layoutPreference.collectAsState(initial = true)
    val viewModel: MainViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name_main)) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.RecycleBin.route) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Recycle Bin")
                    }
                    IconButton(onClick = {
                        viewModel.saveLayoutPreference(!isListView)
                    })
                    {
                        Icon(
                            imageVector = if (isListView) Icons.Default.GridOn else Icons.AutoMirrored.Filled.ViewList,
                            contentDescription = if (isListView) "Switch to Grid" else "Switch to List"
                        )
                    }


                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Detail.withArgs(null))
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_destinasi)
                )
            }
        }
    ) { paddingValues ->
        if (destinasiList.isEmpty()) {
            EmptyState(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        } else {
            if (isListView) {
                DestinasiList(destinasiList, navController, Modifier.padding(paddingValues))
            } else {
                DestinasiGrid(destinasiList, navController, Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun DestinasiList(
    destinasiList: List<Destinasi>,
    navController: NavHostController,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(bottom = 72.dp)
    ) {
        items(destinasiList, key = { it.id }) { destinasi ->
            DestinasiListItem(destinasi) {
                navController.navigate(Screen.Detail.withArgs(destinasi.id))
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun DestinasiGrid(
    destinasiList: List<Destinasi>,
    navController: NavHostController,
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        contentPadding = PaddingValues(bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(destinasiList, key = { it.id }) { destinasi ->
            DestinasiGridItem(destinasi) {
                navController.navigate(Screen.Detail.withArgs(destinasi.id))
            }
        }
    }
}

@Composable
fun DestinasiListItem(destinasi: Destinasi, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp)
    ) {
        Text(
            destinasi.namaObjekWisata,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))
        Text(destinasi.tanggalKeberangkatan, style = MaterialTheme.typography.bodyMedium)
        Text(destinasi.estimasiBiaya, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DestinasiGridItem(destinasi: Destinasi, onItemClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .heightIn(min = 120.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                destinasi.namaObjekWisata,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(6.dp))
            Column {
                Text(destinasi.tanggalKeberangkatan, style = MaterialTheme.typography.bodySmall)
                Text(destinasi.estimasiBiaya, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Delete,
                    contentDescription = stringResource(R.string.hapus))
            }
            if (showDialog) {
                DeleteDialog(
                    onConfirm = {
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.belum_ada_destinasi),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus destinasi ini?") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Hapus") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
