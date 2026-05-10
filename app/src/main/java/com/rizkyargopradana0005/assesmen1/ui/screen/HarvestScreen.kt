package com.rizkyargopradana0005.assesmen1.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Eco
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun HarvestScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val themeColorHex by dataStore.themeColorFlow.collectAsState("#1FC41F")
    val currentThemeColor = Color(themeColorHex.toColorInt())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold
                    )
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
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.info)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        HarvestContent(
            modifier = Modifier.padding(innerPadding),
            themeColor = currentThemeColor
        )
    }
}

@Composable
fun HarvestContent(modifier: Modifier = Modifier, themeColor: Color) {
    var jumlahInput by rememberSaveable { mutableStateOf("") }
    var inputError by rememberSaveable { mutableStateOf(false) }
    var isFarmable by rememberSaveable { mutableStateOf(true) }
    var block by rememberSaveable { mutableFloatStateOf(0f) }
    var seed by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = themeColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Eco, contentDescription = null, tint = themeColor)
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.infoharvest),
                    style = MaterialTheme.typography.bodyMedium,
                    color = themeColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        OutlinedTextField(
            value = jumlahInput,
            onValueChange = {
                jumlahInput = it
                if (inputError) inputError = false
            },
            label = { Text("Jumlah Pohon") },
            placeholder = { Text("Contoh: 100") },
            trailingIcon = { if (inputError) Icon(Icons.Filled.Warning, "error", tint = Color.Red) },
            supportingText = { if (inputError) Text(stringResource(R.string.invalid)) },
            isError = inputError,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = themeColor,
                focusedLabelColor = themeColor
            )
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = isFarmable,
                    onClick = { isFarmable = true },
                    colors = RadioButtonDefaults.colors(selectedColor = themeColor)
                )
                Text("Farmable", style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.width(24.dp))

                RadioButton(
                    selected = !isFarmable,
                    onClick = { isFarmable = false },
                    colors = RadioButtonDefaults.colors(selectedColor = themeColor)
                )
                Text("Unfarmable", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Button(
            onClick = {
                val inputVal = jumlahInput.toIntOrNull() ?: 0
                inputError = (inputVal <= 0)
                if (inputError) return@Button

                block = hitungFarmable(inputVal.toFloat(), isFarmable)
                seed = hitungSeed(inputVal)
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = themeColor)
        ) {
            Text(stringResource(R.string.hitung), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        if (block != 0f) {
            val shareMessage = stringResource(R.string.bagikanHarvest, jumlahInput, block, seed)

            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Hasil Kalkulasi", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                    Spacer(Modifier.height(12.dp))

                    ResultRow(label = "Total Block", value = "${block.toInt()} Block", color = themeColor)
                    HorizontalDivider(Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
                    ResultRow(label = "Estimasi Seed", value = "$seed Seed", color = themeColor)

                    Spacer(Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = { shareData(context, shareMessage) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, themeColor),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = themeColor)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Bagikan Hasil")
                    }
                }
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontWeight = FontWeight.Normal, color = Color.DarkGray)
        Text(value, fontWeight = FontWeight.Bold, color = color, fontSize = 18.sp)
    }
}

private fun hitungFarmable(jumlahInput: Float, isFarmable: Boolean): Float =
    if (isFarmable) jumlahInput * 3.5f else jumlahInput * 2.5f

private fun hitungSeed(jumlahInput: Int): Int = jumlahInput / 5

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Bagikan hasil"))
}