package com.rizkyargopradana0005.assesmen1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rizkyargopradana0005.assesmen1.navigation.SetupNavGraph
import com.rizkyargopradana0005.assesmen1.ui.theme.Assesmen1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assesmen1Theme {
                SetupNavGraph()
            }
        }
    }
}

