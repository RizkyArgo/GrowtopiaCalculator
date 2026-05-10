package com.rizkyargopradana0005.assesmen1.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rizkyargopradana0005.assesmen1.R
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import com.rizkyargopradana0005.assesmen1.navigation.Screen
import com.rizkyargopradana0005.assesmen1.util.SettingsDataStore
import com.rizkyargopradana0005.assesmen1.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)
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
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
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
    ) { innerPadding ->
        ScreenContent(
            showList = showList,
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(data) {
                    ListItem(transaksi = it) {
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
                    GridItem(transaksi = it) {
                        navController.navigate(Screen.EditTransaksi.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(transaksi: Transaksi, onClick: () -> Unit) {
    val isBeli = transaksi.jenis == stringResource(R.string.beli)
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
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
                    color = if (isBeli) Color(31, 196, 31, 40) else Color(244, 67, 54, 40),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = transaksi.jenis.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isBeli) Color(31, 196, 31) else Color(244, 67, 54)
                    )
                }
            }
            Text(
                text = "${transaksi.jumlah} WL",
                color = Color(234, 168, 0),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun GridItem(transaksi: Transaksi, onClick: () -> Unit) {
    val isBeli = transaksi.jenis == stringResource(R.string.beli)
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
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
                color = if (isBeli) Color(31, 196, 31, 40) else Color(244, 67, 54, 40),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = transaksi.jenis.uppercase(),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isBeli) Color(31, 196, 31) else Color(244, 67, 54)
                )
            }
            Text(
                text = "${transaksi.jumlah} WL",
                color = Color(234, 168, 0),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }
    }
}