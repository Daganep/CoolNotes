package ru.coolnotes

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.view.WindowCompat
import com.openkin.domain.utils.NOTIFY_KEY_ID
import ru.coolnotes.navigation.Navigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val notificationId = intent.getIntExtra(NOTIFY_KEY_ID, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val isSOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

        if (isSOrHigher && !alarmManager.canScheduleExactAlarms()) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }

        enableEdgeToEdge()
        setContent {
            Scaffold { scaffoldContentPaddings ->
                Navigation(scaffoldContentPaddings, notificationId)
            }
        }
    }
}
