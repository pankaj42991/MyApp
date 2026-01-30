package com.accord.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.accord.myapp.ui.login.LoginScreen
import com.accord.myapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Entry screen (Login)
                    LoginScreen()
                }
            }
        }
    }
}