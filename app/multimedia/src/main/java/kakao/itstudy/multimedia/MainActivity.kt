package kakao.itstudy.multimedia

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //오디오 재생기와 재생할 오디오 URL 과 현재 재생 중인 위치를 저장할 프로퍼티
    val AUDIO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr"
    private var mediaPlayer: MediaPlayer? = null
    private var playbackPosition = 0

    //재생기 메모리 정리를 위한 함수
    private fun killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer!!.release()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    //오디오를 재생하는 함수
    private fun playAudio(url: String) {
        killMediaPlayer()

        //mediaPlayer = new MediaPlayer();
        //mediaPlayer.setDataSource(url);
        //mediaPlayer.prepare();
        mediaPlayer = MediaPlayer.create(this, R.raw.birthday)
        mediaPlayer!!.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //버튼 찾아오기
        val startBtn: Button = findViewById<Button>(R.id.playBtn)
        val pauseBtn: Button = findViewById<Button>(R.id.pauseBtn)
        val restartBtn: Button = findViewById<Button>(R.id.restartBtn)

        //버튼 클릭 이벤트 처리
        startBtn.setOnClickListener(View.OnClickListener {
            try {
                playAudio(AUDIO_URL)
                Toast.makeText(applicationContext, "음악 파일 재생 시작됨.", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        pauseBtn.setOnClickListener(View.OnClickListener {
            if (mediaPlayer != null) {
                playbackPosition = mediaPlayer!!.currentPosition
                mediaPlayer!!.pause()
                Toast.makeText(applicationContext, "음악 파일 재생 중지됨.", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        restartBtn.setOnClickListener {
            if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
                mediaPlayer!!.seekTo(playbackPosition)
                Toast.makeText(applicationContext, "음악 파일 재생 재시작됨.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        killMediaPlayer()
    }

}