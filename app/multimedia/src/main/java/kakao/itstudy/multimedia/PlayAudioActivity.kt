package kakao.itstudy.multimedia

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PlayAudioActivity : AppCompatActivity() {
    //프로퍼티
    var mList: MutableList<String>? = null
    var mIdx = 0
    var mPlayer: MediaPlayer? = null
    var mPlayBtn: Button? = null
    var mFileName: TextView? = null
    var mProgress: SeekBar? = null
    var wasPlaying = false

    // 액티비티 종료시 재생 강제 종료
    override fun onDestroy() {
        super.onDestroy()
        if (mPlayer != null) {
            mPlayer!!.release()
            mPlayer = null
        }
    }

    //재생 준비를 위한 메소드
    fun prepare(): Boolean {
        try {
            mPlayer!!.prepare()
        } catch (e: IllegalStateException) {
            return false
        } catch (e: IOException) {
            return false
        }
        return true
    }

    //재생 준비 상태를 알려주는 핸들러
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val result = msg.obj as Boolean
            var resultMsg: String? = null
            if (result == true) {
                resultMsg = "재생 준비 완료"
                mFileName!!.text = "파일 : " + mList!![msg.what]
                mProgress!!.max = mPlayer!!.duration
            } else {
                resultMsg = "재생 준비 실패"
            }
            Toast.makeText(this@PlayAudioActivity, resultMsg, Toast.LENGTH_LONG).show()
            if (result == false) {
                finish()
            }
        }
    }

    // 0.2초에 한번꼴로 재생 위치를 갱신해주는 핸들러
    var mProgressHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (mPlayer == null) return
            if (mPlayer!!.isPlaying) {
                mProgress!!.progress = mPlayer!!.currentPosition
            }
            sendEmptyMessageDelayed(0, 200)
        }
    }

    // 재생 위치 이동
    var mOnSeek: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar, progress: Int,
            fromUser: Boolean
        ) {
            if (fromUser) {
                mPlayer!!.seekTo(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            wasPlaying = mPlayer!!.isPlaying
            if (wasPlaying) {
                mPlayer!!.pause()
            }
        }
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    //노래 재생 준비를 해주는 함수
    fun loadMedia(idx: Int) {
        val message = Message()
        message.what = idx
        try {
            Log.e("노래", mList!![idx])
            mPlayer!!.setDataSource(this, Uri.parse(mList!![idx]))
        } catch (e: Exception) {
            message.obj = false
            Log.e("노래1", mList!![idx])
        }
        if (prepare() === false) {
            message.obj = false
            Log.e("노래2", mList!![idx])
        } else {
            message.obj = true
            Log.e("노래3", mList!![idx])
        }
        handler.sendMessage(message)
    }

    //버튼을 누르면 호출하는 함수
    fun mOnClick(v: View) {
        when (v.getId()) {
            R.id.play -> if (mPlayer!!.isPlaying == false) {
                mPlayer!!.start()
                mPlayBtn!!.text = "Pause"
            } else {
                mPlayer!!.pause()
                mPlayBtn!!.text = "Play"
            }
            R.id.stop -> {
                mPlayer!!.stop()
                mPlayBtn!!.text = "Play"
                mProgress!!.progress = 0
                prepare()
            }
            R.id.prev, R.id.next -> {
                val wasPlaying = mPlayer!!.isPlaying
                mIdx = if (v.getId() === R.id.prev) {
                    if (mIdx == 0) mList!!.size - 1 else mIdx - 1
                } else {
                    if (mIdx == mList!!.size - 1) 0 else mIdx + 1
                }
                mPlayer!!.reset()
                loadMedia(mIdx)

                // 이전에 재생중이었으면 다음 곡 바로 재생
                if (wasPlaying) {
                    mPlayer!!.start()
                    mPlayBtn!!.text = "Pause"
                }
            }
        }
    }

    // 재생 완료되면 다음곡으로
    var mOnComplete = MediaPlayer.OnCompletionListener {
        mIdx = if (mIdx == mList!!.size - 1) 0 else mIdx + 1
        mPlayer!!.reset()
        loadMedia(mIdx)
        mPlayer!!.start()
    }

    // 에러 발생시 메시지 출력
    var mOnError =
        MediaPlayer.OnErrorListener { mp, what, extra ->
            val err = ("OnError occured. what = " + what + " ,extra = "
                    + extra)
            Toast.makeText(this@PlayAudioActivity, err, Toast.LENGTH_LONG).show()
            false
        }

    // 위치 이동 완료 처리
    var mOnSeekComplete = MediaPlayer.OnSeekCompleteListener {
        if (wasPlaying) {
            mPlayer!!.start()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_audio)

        // 버튼 찾아오기
        mFileName = findViewById<View>(R.id.filename) as TextView
        mPlayBtn = findViewById<View>(R.id.play) as Button
        mList = mutableListOf<String>()

        //재생 목록을 가져오는 스레드
        object : Thread() {
            override fun run() {
                try {
                    val addr = "http://cyberadam.cafe24.com/song/"
                    val url = URL(addr + "song.txt")
                    val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val br = BufferedReader(InputStreamReader(con.getInputStream()))
                    val sb = StringBuilder()
                    while (true) {
                        val line: String = br.readLine() ?: break
                        sb.append(
                            """
                                $line
                                
                                """.trimIndent()
                        )
                    }
                    val str = sb.toString()
                    val songList =
                        str.split(",".toRegex()).toTypedArray()
                    for (song in songList) {
                        mList!!.add("$addr$song.mp3")
                    }
                    Log.e("목록 준비 완료", mList.toString())
                    mPlayer = MediaPlayer()
                    mIdx = 0
                    loadMedia(mIdx)
                    // 완료 리스너, 시크바 변경 리스너 등록
                    mPlayer!!.setOnCompletionListener(mOnComplete)
                    mPlayer!!.setOnSeekCompleteListener(mOnSeekComplete)
                    mProgress = findViewById<View>(R.id.progress) as SeekBar
                    mProgress!!.setOnSeekBarChangeListener(mOnSeek)
                    mProgressHandler.sendEmptyMessageDelayed(0, 200)
                } catch (e: java.lang.Exception) {
                    Log.e("목록 다운로드 예외", e.localizedMessage)
                    e.printStackTrace()
                }
            }
        }.start()
    }

}