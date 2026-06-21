package com.rizkyargopradana0005.assesmen1.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rizkyargopradana0005.assesmen1.R
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import com.rizkyargopradana0005.assesmen1.model.User
import com.rizkyargopradana0005.assesmen1.navigation.Screen
import com.rizkyargopradana0005.assesmen1.network.UserDataStore
import com.rizkyargopradana0005.assesmen1.util.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
    LaunchedEffect(true) {
        viewModel.retrieveData()
    }
    val context = LocalContext.current
    val settingsDataStore = SettingsDataStore(context)
    val userDataStore = UserDataStore(context)

    val showList by settingsDataStore.layoutFlow.collectAsState(true)
    val themeColorHex by settingsDataStore.themeColorFlow.collectAsState("#1FC41F")
    val currentTheme = Color(themeColorHex.toColorInt())
    val user by userDataStore.userFlow.collectAsState(initial = User())

    var showProfileDialog by remember { mutableStateOf(false) }


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
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = currentTheme,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { showProfileDialog = true }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Profil",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            settingsDataStore.saveLayout(
                                !showList
                            )
                        }
                    },
                    containerColor = currentTheme,
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(if (showList) R.drawable.baseline_grid_view_24 else R.drawable.baseline_view_list_24),
                        contentDescription = "Ganti Layout"
                    )
                }
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.Transaksi.route) },
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tambah),
                        contentDescription = stringResource(R.string.tambah_transaksi),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    ) { innerPadding ->
        ScreenContent(
            showList = showList,
            Modifier.padding(innerPadding),
            navController, currentTheme,
            viewModel = viewModel
        )

        if (showProfileDialog) {
            ProfileDialog(
                user = user,
                onDismiss = { showProfileDialog = false },
                onLogout = {
                    CoroutineScope(Dispatchers.IO).launch {
                        userDataStore.saveData(
                            User(
                                "",
                                "",
                                ""
                            )
                        )
                    }
                    showProfileDialog = false
                    navController.navigate(Screen.Home.route) { popUpTo(0) { inclusive = true } }
                }
            )
        }
    }
}

@Composable
fun ProfileDialog(user: User, onDismiss: () -> Unit, onLogout: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Profil Pengguna") },
        text = {
            Column {
                Text("Nama: ${user.name.ifEmpty { "Tidak ada nama" }}")
                Text("Email: ${user.email.ifEmpty { "Belum login" }}")
            }
        },
        confirmButton = { TextButton(onClick = onLogout) { Text("Logout", color = Color.Red) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Tutup") } }
    )
}

@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    themeColor: Color,
    viewModel: MainViewModel
) {
    val data by viewModel.data.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsState()

    when {
        isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        data.isEmpty() -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.kosong))
            }
        }

        else -> {
            if (showList) {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(data) {
                        ListItem(transaksi = it, themeColor = themeColor) {
                            navController.navigate(Screen.EditTransaksi.withId(it.id))
                        }
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    modifier = modifier.fillMaxSize(),
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 12.dp,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 84.dp)
                ) {
                    items(data) {
                        GridItem(transaksi = it, themeColor = themeColor) {
                            navController.navigate(Screen.EditTransaksi.withId(it.id))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(transaksi: Transaksi, themeColor: Color, onClick: () -> Unit) {
    val isBeli = transaksi.jenis == stringResource(R.string.beli)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaksi.judul,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = if (isBeli) themeColor.copy(alpha = 0.15f) else Color(244, 67, 54, 40),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = transaksi.jenis.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isBeli) themeColor else Color(244, 67, 54)
                    )
                }
            }
            Text(
                text = "${transaksi.harga} WL",
                color = Color(234, 168, 0),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun GridItem(transaksi: Transaksi, themeColor: Color, onClick: () -> Unit) {
    val isBeli = transaksi.jenis == stringResource(R.string.beli)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = transaksi.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Surface(
                color = if (isBeli) themeColor.copy(alpha = 0.15f) else Color(244, 67, 54, 40),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = transaksi.jenis.uppercase(),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isBeli) themeColor else Color(244, 67, 54)
                )
            }
            Text(
                text = "${transaksi.harga} WL",
                color = Color(234, 168, 0),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }
    }
}