package kakao.itstudy.multimedia

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import java.io.IOException

class RecordActivity : AppCompatActivity() {
    // 음성 녹음 권한요청 코드
    val REQUEST_CODE_RECORD_AUDIO_PERMISSION = 200
    // 비디오 캡쳐 요청코드
    val REQUEST_CODE_VIDEO_CAPTURE = 1
    // 녹음된 음성파일 이름
    var voiceFileName: String = ""
    // 음성 녹음을 위한 MediaRecoder
    var mediaRecorder: MediaRecorder? = null
    // 음성플레이를 위한 MediaPlayer
    var mediaPlayer: MediaPlayer? = null
    // 녹음이 시작되었는지 상태 변수
    var isRecordStart = false
    // 음성 플레이가 시작되었는지 상태 변수
    var isStartPlaying = false
    // 로그에서 사용할 태그
    val LOG_TAG = "MainActivity"
    // 앱에서 필요로 하는 권한 배열
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // Runtime 에 권한 요청
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_RECORD_AUDIO_PERMISSION)
        // 저장될 음성파일 위치 지정
        voiceFileName = "${externalCacheDir!!.absolutePath}/voice_record.3gp"

        val voiceRecordingButton = findViewById<Button>(R.id.voiceRecordingButton)
        val voicePlayButton = findViewById<Button>(R.id.voicePlayButton)
        val videoRecordingButton = findViewById<Button>(R.id.videoRecordingButton)

        // 음성 녹음버튼이 클릭된 경우
        voiceRecordingButton.setOnClickListener {
            if (isRecordStart) {
                stopRecording()
            } else {
                startRecording()
            }
            isRecordStart = !isRecordStart

            voiceRecordingButton.text = when (isRecordStart) {
                true -> "음성 녹음 정지"
                false -> "음성 녹음 시작"
            }
        }

        // 음성 재생버튼이 클릭된 경우
        voicePlayButton.setOnClickListener {
            if(isStartPlaying) stopPlaying() else startPlaying()
            isStartPlaying = !isStartPlaying
            voicePlayButton.text = when (isStartPlaying) {
                true -> "음성 재생 정지"
                false -> "음성 재생 시작"
            }
        }

        // 비디오 버튼이 클릭되면 이미 설치된 카메라에 비디오 캡쳐를 요청하는 Intent 를 보낸다.
        videoRecordingButton.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                takeVideoIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_CODE_VIDEO_CAPTURE)
                }
            }
        }

    }

    // MediaPlayer 로 오디오파일 재생
    fun startPlaying() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(voiceFileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
    }
    // 오디오 파일 재생 중지
    fun stopPlaying() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // 녹음 시작
    fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(voiceFileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
    }

    // 녹음 중지
    fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    // Activity 가 화면에서 사라지면 음성녹음 및 플레이어를 해제한다.
    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // 비디오 캡쳐를 요청한 카메라 액티비티가 완료된 후 호출
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri = intent?.data
            val videoView = findViewById<VideoView>(R.id.videoView)
            videoView.setMediaController(MediaController(this))
            videoView.setVideoURI(videoUri)
            videoView.requestFocus()
            videoView.start()
        }
    }

    // 권한 요청이 완료된 경우 호출되는 함수
    // 권한을 거절한 경우 종료
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionToRecordAccepted = if (requestCode == REQUEST_CODE_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

}