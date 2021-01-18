package kakao.itstudy.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_constellation.*
import java.text.SimpleDateFormat
import java.util.*

class ConstellationActivity : AppCompatActivity() {
    /* 전달받은 월정보, 일정보 기준으로 별자리를 반환한다. */
    fun makeConstellationString(month: Int, day: Int): String {
        // 전달받은 월 정보와 일 정보를 기반으로 정수형태의 값을 만든다.
        // ex) 1월 5일 --> 105, 11월 1일 --> 1101
        val target = "${month + 1}${String.format("%02d", day)}".toInt()
        when (target) {
            in 101..119 -> return "염소자리"
            in 120..218 -> return "물병자리"
            in 219..320 -> return "물고기자리"
            in 321..419 -> return "양자리"
            in 420..520 -> return "황소자리"
            in 521..621 -> return "쌍둥이자리"
            in 622..722 -> return "게자리"
            in 723..822 -> return "사자자리"
            in 823..923 -> return "처녀자리"
            in 924..1022 -> return "천칭자리"
            in 1023..1122 -> return "전갈자리"
            in 1123..1224 -> return "사수자리"
            in 1225..1231 -> return "염소자리"
            else -> return "기타별자리"
        }
    }

    fun getLottoNumbersFromHash(str: String): MutableList<Int> {
        // 1 ~ 45 번에 로또 번호를 저장할 리스트 생성
        val list = mutableListOf<Int>()

        // 1~45 까지 for 문을 돌면서 리스트에 로또 번호 저장
        for (number in 1..45) {
            list.add(number)
        }

        // SimpleDateFormat 은 날짜의 시간값을 포맷화된 텍스트 형태로 바꿔주는 클래스
        // 현재 Date 의 "yyyy-MM-dd" 문자열과 이름 문자열을 합친다
        val targetString = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date()) + str

        // 리스트를 무작위로 섞는다. 이때 섞는 기준으로 Random(SEED) 를 사용한다
        // SEED 값은 전달받은 이름과 오늘의 해당하는 "yyyy-MM-dd" 를 합친 문자열의 해시코드를 사용한다.
        list.shuffle(Random(targetString.hashCode().toLong()))

        // 리스트를 앞에서부터 순서대로 6개를 짤라 결과 반환
        return list.subList(0, 6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)

        goResultButton.setOnClickListener {
            // ResultActivity 를 시작하는 Intent 생성
            val intent = Intent(this, ResultActivity::class.java)

            // intent 의 결과 데이터를 전달한다.
            // int 의 리스트를 전달하므로 putIntegerArrayListExtra 를 사용한다.
            // 전달하는 리스트는 별자리의 해시코드로 생성한 로또번호
            intent.putIntegerArrayListExtra("result", ArrayList(getLottoNumbersFromHash(makeConstellationString(datePicker.month, datePicker.dayOfMonth))))

            // 별자리를 추가로 전달한다.
            intent.putExtra("constellation", makeConstellationString(datePicker.month, datePicker.dayOfMonth))

            // ResultActivity 를 시작하는 Intent 를 만들고 startActivity 로 실행
            startActivity(intent)

        }

        // 현재 DatePicker 의 월, 일 정보로 별자리 텍스트 변경
        textView.text = makeConstellationString(datePicker.month, datePicker.dayOfMonth)

        // DatePicker 의 날짜가 변화하면 별자리를 보여주는 텍스트뷰도 변경
        val calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), object : CalendarView.OnDateChangeListener, DatePicker.OnDateChangedListener {
            override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                // 변경된 시점의 DatePicker 의 월, 일 정보로 별자리 텍스트 변경
                textView.text = makeConstellationString(datePicker.month, datePicker.dayOfMonth)
            }
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {}
        })


        backButton.setOnClickListener{
            finish()
        }
    }
}