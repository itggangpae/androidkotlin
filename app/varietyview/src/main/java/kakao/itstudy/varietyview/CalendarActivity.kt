package kakao.itstudy.varietyview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(
                this@CalendarActivity, "" + year + "/" +
                        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_LONG
            ).show()
        }

    }
}