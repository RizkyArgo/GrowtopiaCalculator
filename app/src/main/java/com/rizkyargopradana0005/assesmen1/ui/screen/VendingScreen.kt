package com.rizkyargopradana0005.assesmen1.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rizkyargopradana0005.assesmen1.R
import com.rizkyargopradana0005.assesmen1.navigation.Screen
import com.rizkyargopradana0005.assesmen1.util.SettingsDataStore
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendingScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val themeColorHex by dataStore.themeColorFlow.collectAsState("#1FC41F")
    val currentThemeColor = Color(themeColorHex.toColorInt())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = currentThemeColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.info),
                            tint = Color.White
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        VendingContent(
            modifier = Modifier.padding(innerPadding),
            themeColor = currentThemeColor
        )
    }
}

@Composable
fun VendingContent(modifier: Modifier = Modifier, themeColor: Color) {
    var jumlahInput by rememberSaveable { mutableStateOf("") }
    var modal by rememberSaveable { mutableStateOf("") }
    var hargaInput by rememberSaveable { mutableStateOf("") }
    var inputError by rememberSaveable { mutableStateOf(false) }
    var perItem by rememberSaveable { mutableStateOf(true) }
    var hasilJual by rememberSaveable { mutableIntStateOf(0) }
    var profit by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hitung Penjualan",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = modal,
            onValueChange = { modal = it },
            label = { Text("Modal / Harga Beli (WL)") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.wl),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            isError = inputError,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = themeColor,
                focusedLabelColor = themeColor
            )
        )

        OutlinedTextField(
            value = jumlahInput,
            onValueChange = { jumlahInput = it },
            label = { Text("Jumlah Barang") },
            shape = RoundedCornerShape(12.dp),
            isError = inputError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = themeColor,
                focusedLabelColor = themeColor
            )
        )

        OutlinedTextField(
            value = hargaInput,
            onValueChange = { hargaInput = it },
            label = { Text("Harga Jual (WL)") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.wl),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = { if (inputError) Icon(Icons.Filled.Warning, "error", tint = Color.Red) },
            supportingText = { if (inputError) Text(stringResource(R.string.invalid)) },
            isError = inputError,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = themeColor,
                focusedLabelColor = themeColor
            )
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                RadioButton(
                    selected = perItem,
                    onClick = { perItem = true },
                    colors = RadioButtonDefaults.colors(selectedColor = themeColor)
                )
                Text(text = "WL/Item", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = !perItem,
                    onClick = { perItem = false },
                    colors = RadioButtonDefaults.colors(selectedColor = themeColor)
                )
                Text(text = "Item/WL", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Button(
            onClick = {
                val jml = jumlahInput.toIntOrNull() ?: 0
                val hrg = hargaInput.toIntOrNull() ?: 0
                val mdl = modal.toIntOrNull() ?: 0

                inputError = (jml <= 0 || hrg <= 0)
                if (inputError) return@Button

                hasilJual = hitung(jml, hrg, perItem)
                profit = untung(mdl, hasilJual)
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = themeColor)
        ) {
            Text(text = stringResource(R.string.hitung), fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (hasilJual != 0) {
            val shareMessage = stringResource(R.string.bagikanPenjualan, modal, hasilJual, profit)

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Hasil Analisis", color = Color.Gray, style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(12.dp))

                    ResultLine(label = "Modal Total", value = "$modal WL")
                    ResultLine(label = "Hasil Kotor", value = "$hasilJual WL")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    if (profit >= 0) {
                        ResultLine(label = "Hasil Bersih", value = "$profit WL", valueColor = themeColor, isBold = true)
                    } else {
                        ResultLine(label = "Rugi", value = "$profit WL", valueColor = Color.Red, isBold = true)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { shareData(context, shareMessage) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(text = stringResource(R.string.bagikan))
                    }
                }
            }
        }
    }
}

@Composable
fun ResultLine(label: String, value: String, valueColor: Color = Color.Black, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.wl),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = valueColor,
                fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Bold
            )
        }
    }
}

private fun hitung(jumlahInput: Int, harga: Int, perItem: Boolean): Int {
    return if (perItem) {
        jumlahInput * harga
    } else {
        if (harga > 0) jumlahInput / harga else 0
    }
}

private fun untung(modal: Int, hasil: Int): Int {
    return hasil - modal
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Bagikan hasil"))
}