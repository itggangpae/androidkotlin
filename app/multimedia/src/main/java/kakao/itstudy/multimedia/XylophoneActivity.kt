package kakao.itstudy.multimedia

import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class XylophoneActivity : AppCompatActivity() {
    //노래 목록 만들기
    private val sounds = listOf(
        Pair(R.id.do1, R.raw.do1),
        Pair(R.id.re, R.raw.re),
        Pair(R.id.mi, R.raw.mi),
        Pair(R.id.fa, R.raw.fa),
        Pair(R.id.sol, R.raw.sol),
        Pair(R.id.la, R.raw.la),
        Pair(R.id.si, R.raw.si),
        Pair(R.id.do2, R.raw.do2)
    )
    //사운드 풀 생성
    private val soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        SoundPool.Builder().setMaxStreams(8).build()
    } else {
        SoundPool(8, AudioManager.STREAM_MUSIC, 0)
    }

    //노래를 가져오고 텍스트 뷰의 클릭 이벤트 처리를 위한 함수
    private fun tune(pitch: Pair<Int, Int>) {
        val soundId = soundPool.load(this, pitch.second, 1)
        findViewById<TextView>(pitch.first).setOnClickListener {
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xylophone)

        //노래 목록에 이벤트 연결
        sounds.forEach { tune(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}