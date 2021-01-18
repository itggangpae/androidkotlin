package kakao.itstudy.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    //1~45까지 의 숫자를 랜덤하게 리턴하는 함수
    fun getRandomLottoNumber(): Int {
        return Random().nextInt(45) + 1
    }

    //1~45까지의 중복되지 않은 6개의 숫자를 List로 만들어서 리턴하는 함수
    fun getRandomLottoNumbers(): MutableList<Int> {

        val lottoNumbers = mutableListOf<Int>()

        // 6번 반복하는 for 문
        for (i in 1..6) {
            // 랜덤한 번호를 임시로 저장할 변수를 생성
            var number = 0
            do {
                // 랜덤한 번호를 추출해 number 변수에 저장
                number = getRandomLottoNumber()

                // lottoNumbers 에 number 변수의 값이 없을때까지 반복
            } while (lottoNumbers.contains(number))

            // 이미 뽑은 리스트에 없는 번호가 나올때까지 반복했으므로 중복이 없는 상태
            // 추출된 번호를 뽑은 리스트에 추가
            lottoNumbers.add(number)
        }
        return lottoNumbers
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 랜덤으로 번호 생성 카드의 클릭 이벤트 리스너
        randomCard.setOnClickListener {
            // ResultActivity 를 시작하는 Intent 생성
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(getRandomLottoNumbers()))

            // ResultActivity 를 시작하는 Intent 를 만들고 startActivity 로 실행
            startActivity(intent)
        }

        // 별자리로 번호 생성 카드의 클릭 이벤트 리스너
        constellationCard.setOnClickListener {
            // ConstellationActivity 를 시작하는 Intent 를 만들고 startActivity 로 실행
            startActivity(Intent(this, ConstellationActivity::class.java))
        }

        // 이름으로 번호 생성 카드의 클릭 이벤트 리스너
        nameCard.setOnClickListener {
            // NameActivity 를 시작하는 Intent 를 만들고 startActivity 로 실행
            startActivity(Intent(this, NameActivity::class.java))
        }

    }
}
