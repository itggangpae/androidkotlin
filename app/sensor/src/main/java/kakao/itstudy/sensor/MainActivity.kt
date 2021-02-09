package kakao.itstudy.sensor

import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 위치 관리자 구함
        val LocMan =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 제공자 목록 구해서 출력
        val arProvider = LocMan.getProviders(false)
        var msg = ""
        for (i in arProvider.indices) {
            msg += """Provider $i : ${arProvider[i]}
"""
        }
        // 최적의 제공자 조사
        //옵션 설정
        val crit = Criteria()
        crit.accuracy = Criteria.NO_REQUIREMENT
        crit.powerRequirement = Criteria.NO_REQUIREMENT
        crit.isAltitudeRequired = false
        crit.isCostAllowed = false
        val best = LocMan.getBestProvider(crit, true)
        msg += "\nbest provider : $best\n\n"
        // GPS와 네트워크 제공자 사용 가능성 조사
        msg += """${LocationManager.GPS_PROVIDER} : ${LocMan.isProviderEnabled(LocationManager.GPS_PROVIDER)}
"""
        msg += """${LocationManager.NETWORK_PROVIDER} : ${LocMan.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )}
"""
        // 결과 출력
        val result = findViewById<TextView>(R.id.result)
        result.text = msg
    }
}