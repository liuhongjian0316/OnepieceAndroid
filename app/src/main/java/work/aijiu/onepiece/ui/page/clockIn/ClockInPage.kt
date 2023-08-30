package work.aijiu.onepiece.ui.page.clockIn

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import work.aijiu.onepiece.R
import work.aijiu.onepiece.service.AlarmReceiver
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.ui.theme.*
import work.aijiu.onepiece.ui.view.bar.OnePieceAppBar
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClockInPage(
    actions: OnePieceActions,
) {

    val alarmClock = AlarmClock(LocalContext.current)
    val alarmTimeList = listOf(
        Pair(7, 0),
        Pair(12, 0),
        Pair(12, 31),
        Pair(17, 0),
    )
    alarmClock.setAlarmList(alarmTimeList)

    val alarms = listOf(
        Alarm(
            time = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 7)
                set(Calendar.MINUTE, 50)
                set(Calendar.SECOND, 0)
            }.timeInMillis, enabled = true
        ),
        Alarm(
            time = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }.timeInMillis, enabled = true
        ),

        Alarm(
            time = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 31)
                set(Calendar.SECOND, 0)
            }.timeInMillis, enabled = true
        ),

        Alarm(
            time = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 17)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }.timeInMillis, enabled = true
        ),
    )

    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Alarm")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            OnePieceAppBar(stringResource(R.string.clock_in), click = {
                actions.upPress()
            })

            CurrentTime()

            LazyColumn(
                state = lazyListState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            ) {
                itemsIndexed(items = alarms) { _, item ->
                    AlarmItem(item)
                }
            }
        }

    }


}

@Composable
fun CurrentTime() {
    val currentTime = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            val dateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
            currentTime.value = dateFormat.format(Date())
            delay(1000)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "${currentTime.value}", fontSize = 32.sp)
    }
}

@Composable
fun AlarmItem(alarm: Alarm) {
    Card(
        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp, 12.dp,6.dp,12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${SimpleDateFormat("hh-mm").format(alarm.time)}", fontSize = 24.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "麻溜打卡！！！", fontSize = 18.sp)
        }
    }
}

data class Alarm(
    val time: Long,
    val enabled: Boolean = true,
)

// 闹钟
class AlarmClock(private val context: Context) {

    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null
    private var pendingIntentList: MutableList<PendingIntent> = mutableListOf()


    fun setAlarm(timeInMillis: Long) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        pendingIntent =
            PendingIntent.getBroadcast(context, 0X102, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager?.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        }
    }

    fun setDailyAlarm(hour: Int, minute: Int) {

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0X102, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        if (pendingIntent != null && alarmManager != null) {
            alarmManager!!.cancel(pendingIntent)
        }
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun setAlarmList(alarmTimeList: List<Pair<Int, Int>>) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for ((hour, minute) in alarmTimeList) {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0X102, intent, PendingIntent.FLAG_IMMUTABLE)
            }
            pendingIntentList.add(pendingIntent!!)

            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    fun cancelAlarm() {
        alarmManager?.cancel(pendingIntent)
    }

    fun cancelAllAlarm() {
        for (pendingIntent in pendingIntentList) {
            alarmManager?.cancel(pendingIntent)
        }
    }
}

