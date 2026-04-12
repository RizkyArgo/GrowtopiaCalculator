package com.rizkyargopradana0005.assesmen1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rizkyargopradana0005.assesmen1.model.Home
import com.rizkyargopradana0005.assesmen1.ui.theme.Assesmen1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assesmen1Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun ScreenContent(home: Home,modifier: Modifier = Modifier) {
   Column (
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Image(painterResource(id = home.imageResId),
           contentDescription = "",
           contentScale = ContentScale.Crop,
           modifier = Modifier.size(132.dp)
               .clickable{

               }
       )
       Text(text = home.nama, modifier = Modifier.padding(top = 8.dp)
       )
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val data = listOf(
        Home("Hitung Harvest", R.drawable.tree),
        Home("Hitung Penjualan",R.drawable.vending)
    )
    Scaffold(
        topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color(31, 196, 31, 255),
                    titleContentColor = androidx.compose.ui.graphics.Color(255,255,255),
                    )
                )
        },
    ) { innerPadding -> androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScreenContent(data[0])
        ScreenContent(data[1])

        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assesmen1Theme {
        MainScreen()
    }
}