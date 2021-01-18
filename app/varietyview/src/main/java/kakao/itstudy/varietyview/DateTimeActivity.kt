package kakao.itstudy.varietyview

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_date_time.*
import java.util.*

class DateTimeActivity : AppCompatActivity() {
    var mYear = 0
    var month:Int = 0
    var day:Int = 0
    var hour:Int = 0
    var minute:Int = 0

    fun updateNow() {
        txtDate!!.text = String.format(
            "%d/%d/%d", mYear,
            month + 1, day
        )
        txtTime!!.text = String.format("%d:%d", hour, minute)
    }

    var mDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mYear = year
            month = monthOfYear
            day = dayOfMonth
            updateNow()
        }

    var mTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var minute = minute
            hour = hourOfDay
            minute = minute
            updateNow()
        }


    fun mOnClick(v: View) {
        when (v.id) {
            R.id.btnchangedate -> DatePickerDialog(
                this@DateTimeActivity, mDateSetListener,
                mYear, month, day
            ).show()
            R.id.btnchangetime -> TimePickerDialog(
                this@DateTimeActivity, mTimeSetListener,
                hour, minute, false
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time)

        val cal: Calendar = GregorianCalendar()
        mYear = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
        updateNow()

    }
}