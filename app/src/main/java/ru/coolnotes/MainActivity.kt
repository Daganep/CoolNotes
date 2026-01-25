package ru.coolnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.view.WindowCompat
import ru.coolnotes.navigation.Navigation
import ru.coolnotes.ui.theme.CoolNotesTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            CoolNotesTheme {
                Scaffold { scaffoldContentPaddings ->
                    Navigation(scaffoldContentPaddings)
                }
            }
        }
    }
}
