package kakao.itstudy.multimedia

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class VideoPlayActivity : AppCompatActivity() {
    val VIDEO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4"
    private val videoView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        val startBtn: Button = findViewById<Button>(R.id.startBtn)
        val volumeBtn: Button = findViewById<Button>(R.id.volumeBtn)
        val videoView = findViewById<VideoView>(R.id.videoView)

        startBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                videoView!!.seekTo(0)
                videoView!!.start()
            }
        })

        volumeBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val mAudioManager =
                    getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                mAudioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    maxVolume,
                    AudioManager.FLAG_SHOW_UI
                )
            }
        })

        val mc = MediaController(this)
        videoView!!.setMediaController(mc)
        videoView!!.setVideoURI(Uri.parse(VIDEO_URL))
        videoView!!.requestFocus()

        videoView!!.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            Toast.makeText(
                applicationContext,
                "동영상이 준비되었습니다.\n'재생' 버튼을 누르세요.",
                Toast.LENGTH_LONG
            ).show()
        })

        videoView!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            Toast.makeText(applicationContext, "동영상 재생이 완료되었습니다.", Toast.LENGTH_LONG)
                .show()
        })
    }

    override fun onResume() {
        Toast.makeText(applicationContext, "동영상 준비중입니다.\n잠시 기다려주세요.", Toast.LENGTH_LONG).show()
        super.onResume()
    }

}