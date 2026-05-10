package com.rizkyargopradana0005.assesmen1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rizkyargopradana0005.assesmen1.R
import com.rizkyargopradana0005.assesmen1.navigation.Screen
import com.rizkyargopradana0005.assesmen1.ui.theme.Assesmen1Theme
import com.rizkyargopradana0005.assesmen1.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var judul by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }
    var jenisTransaksi by remember { mutableStateOf("Beli") }

    LaunchedEffect(id) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTransaksi(id) ?: return@LaunchedEffect
        judul = data.judul
        jumlah = data.jumlah
        jenisTransaksi = data.jenis
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (id == null) stringResource(R.string.tambah_transaksi) else stringResource(R.string.edit_transaksi),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(31, 196, 31),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.info),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormTransaksi(
            title = judul,
            onTitleChange = { judul = it },
            nominal = jumlah,
            onJumlahChange = { jumlah = it },
            jenisTransaksi = jenisTransaksi,
            onJenisChange = { jenisTransaksi = it },
            modifier = Modifier.padding(padding),
            isEdit = id != null,
            onSave = {
                if (judul.isBlank() || jumlah.isBlank()) {
                    Toast.makeText(context, R.string.invalid_transaksi, Toast.LENGTH_SHORT).show()
                } else {
                    if (id == null) viewModel.insert(judul, jumlah, jenisTransaksi)
                    else viewModel.update(id, judul, jumlah, jenisTransaksi)
                    navController.popBackStack()
                }
            },
            onDelete = {
                if (id != null) {
                    viewModel.delete(id)
                    navController.popBackStack()
                    Toast.makeText(context, R.string.sukses, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@Composable
fun FormTransaksi(
    title: String,
    onTitleChange: (String) -> Unit,
    nominal: String,
    onJumlahChange: (String) -> Unit,
    jenisTransaksi: String,
    onJenisChange: (String) -> Unit,
    modifier: Modifier,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    isEdit: Boolean
) {
    val opsiBeli = stringResource(R.string.beli)
    val opsiJual = stringResource(R.string.jual)

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Tipe Transaksi",
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val isBeliSelected = jenisTransaksi == opsiBeli
                Button(
                    onClick = { onJenisChange(opsiBeli) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBeliSelected) Color(31, 196, 31) else Color(240, 240, 240),
                        contentColor = if (isBeliSelected) Color.White else Color.Gray
                    )
                ) {
                    Text(text = opsiBeli, fontWeight = FontWeight.Bold)
                }

                val isJualSelected = jenisTransaksi == opsiJual
                Button(
                    onClick = { onJenisChange(opsiJual) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isJualSelected) Color(33, 150, 243) else Color(240, 240, 240),
                        contentColor = if (isJualSelected) Color.White else Color.Gray
                    )
                ) {
                    Text(text = opsiJual, fontWeight = FontWeight.Bold)
                }
            }
        }

        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(stringResource(R.string.judul)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = nominal,
            onValueChange = onJumlahChange,
            label = { Text(stringResource(R.string.nominal)) },
            suffix = { Text(" WL", fontWeight = FontWeight.Bold, color = Color(0xFFDAA520)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(31, 196, 31))
            ) {
                Text(stringResource(R.string.simpan), color = Color.White, style = MaterialTheme.typography.titleMedium)
            }

            if (isEdit) {
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(244, 67, 54)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(244, 67, 54))
                ) {
                    Text(stringResource(R.string.hapus), style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesmen1Theme {
        DetailScreen(rememberNavController())
    }
}