package com.openkin.presentation.ui.addnote.widgets

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.openkin.domain.utils.URI_SCHEME
import com.openkin.presentation.R
import com.openkin.presentation.ui.dialogs.ConfirmDialog
import com.openkin.presentation.ui.dialogs.RestrictedTimePickerDialog
import com.openkin.presentation.ui.theme.black
import com.openkin.presentation.ui.theme.datePickerButton
import com.openkin.presentation.utils.SIMPLE_NOTE_DATE_FORMAT
import com.openkin.presentation.utils.addZero
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun TargetDate(
    selectedDate: LocalDate,
    selectedTime: Pair<Int, Int>?,
    isNoteArchived: Boolean = false,
    isNotifyWasRequested: Boolean,
    modifier: Modifier,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (Pair<Int, Int>?) -> Unit,
    updateNotifyRequestStatus: () -> Unit,
) {
    var openConfirmDialog by remember { mutableStateOf(false) }
    val activity = LocalActivity.current as ComponentActivity
    val datesBorderWidth = 0.5
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateChanged(LocalDate.of(selectedYear, selectedMonth + 1, selectedDay))
        },
        selectedDate.year,
        selectedDate.month.value - 1,
        selectedDate.dayOfMonth,
    )
    val timePickerDialog = RestrictedTimePickerDialog(
        context = LocalContext.current,
        selectedDate = selectedDate,
        selectedHour = selectedTime?.first ?: hour,
        selectedMinute = selectedTime?.second ?: minute,
        onTimeChanged = onTimeChanged,
    )
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) timePickerDialog.show()
        }
    val timerText = if (selectedTime == null) {
        stringResource(R.string.add_note_empty_timer)
    } else {
        "${addZero(selectedTime.first)}:${addZero(selectedTime.second)}"
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .weight(1F)
                .clickable { datePickerDialog.show() }
                .border(
                    width = datesBorderWidth.dp,
                    color = black,
                    shape = RoundedCornerShape(5.dp),
                )
                .padding(all = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.image_target_calendar),
                contentDescription = stringResource(R.string.add_note_target_date_icon),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = DateTimeFormatter.ofPattern(SIMPLE_NOTE_DATE_FORMAT).format(selectedDate),
                style = MaterialTheme.typography.datePickerButton,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        val notifyIsPossible = selectedDate >= LocalDate.now()
        if (!isNoteArchived && notifyIsPossible) {
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                val permission = Manifest.permission.POST_NOTIFICATIONS
                                val checkSelfPermission = activity.checkSelfPermission(permission)
                                if (checkSelfPermission == PERMISSION_GRANTED) {
                                    timePickerDialog.show()
                                } else {
                                    openConfirmDialog = true
                                }
                            } else {
                                timePickerDialog.show()
                            }
                        }
                        .border(
                            width = datesBorderWidth.dp,
                            color = black,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .padding(all = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                ) {
                    Image(
                        painter = painterResource(R.drawable.image_timer),
                        contentDescription = stringResource(R.string.add_note_timer_icon),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(24.dp),
                    )
                    Text(
                        text = timerText,
                        style = MaterialTheme.typography.datePickerButton,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 16.dp),
                    )
                }
            }
        }
        AnimatedVisibility(visible = (selectedTime != null)) {
            Image(
                painter = painterResource(R.drawable.icon_clear_edit_text),
                contentDescription = stringResource(R.string.search_screen_clear_search_field),
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onTimeChanged(null) }
                    ),
            )
        }
        if (openConfirmDialog) {
            ConfirmDialog(
                onDismissRequest = { openConfirmDialog = false },
                onConfirmation = {
                    openConfirmDialog = false
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return@ConfirmDialog
                    val permission = Manifest.permission.POST_NOTIFICATIONS
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        requestPermissionLauncher.launch(permission)
                    } else if (isNotifyWasRequested) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri =
                            Uri.fromParts(URI_SCHEME, activity.applicationContext.packageName, null)
                        intent.data = uri
                        activity.startActivity(intent)
                    } else {
                        requestPermissionLauncher.launch(permission)
                        updateNotifyRequestStatus()
                    }
                },
                confirmButtonText = stringResource(R.string.confirm_button_notify_permission),
                dismissButtonText = stringResource(R.string.dismiss_button_notify_permission),
                dialogText = stringResource(R.string.text_get_notify_permission),
                iconId = R.drawable.image_exclamation_mark,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetDateWithTimePreview() {
    TargetDate(
        selectedDate = LocalDate.now(),
        selectedTime = Pair(5, 5),
        isNoteArchived = false,
        isNotifyWasRequested = false,
        modifier = Modifier,
        onDateChanged = {},
        onTimeChanged = {},
        updateNotifyRequestStatus = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun TargetDateWithoutTimePreview() {
    TargetDate(
        selectedDate = LocalDate.now(),
        selectedTime = null,
        isNoteArchived = false,
        isNotifyWasRequested = false,
        modifier = Modifier,
        onDateChanged = {},
        onTimeChanged = {},
        updateNotifyRequestStatus = {},
    )
}
