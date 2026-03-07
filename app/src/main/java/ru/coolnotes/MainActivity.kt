package ru.coolnotes

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowCompat
import com.openkin.domain.utils.NOTIFY_KEY_ID
import com.openkin.presentation.ui.theme.CoolNotesTheme
import ru.coolnotes.navigation.Navigation

class MainActivity : ComponentActivity() {

//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (!isGranted) {
//                //TODO сообщить пользователю что уведомления отключены
//            }
//        }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val notificationId = intent.getIntExtra(NOTIFY_KEY_ID, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val isTiramisuOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val isSOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        val isNotificationEnabled = NotificationManagerCompat
            .from(this)
            .areNotificationsEnabled()

//        if (isTiramisuOrHigher && !isNotificationEnabled) {
//            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//        }

        if (isSOrHigher && !alarmManager.canScheduleExactAlarms()) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }

        enableEdgeToEdge()
        setContent {
            CoolNotesTheme {
                Scaffold { scaffoldContentPaddings ->
                    Navigation(scaffoldContentPaddings, notificationId)
                }
            }
        }
    }
}
