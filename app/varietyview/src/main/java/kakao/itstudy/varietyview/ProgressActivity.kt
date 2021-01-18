package kakao.itstudy.varietyview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val eventHandler: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View) {
                when (v.getId()) {
                    R.id.start -> {
                        progressbar?.progress = progressbar!!.progress + 10
                        progressind?.setVisibility(View.VISIBLE)
                    }
                    R.id.stop -> {
                        progressbar?.progress = progressbar!!.progress - 10
                        progressind?.setVisibility(View.GONE)
                    }
                }
            }
        }

        start.setOnClickListener(eventHandler)
        stop.setOnClickListener(eventHandler)

        seekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                volume?.setText("볼륨 : $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@ProgressActivity, "볼륨 조절시작", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@ProgressActivity, "볼륨 조절 종료", Toast.LENGTH_SHORT).show()
            }
        })

        rating!!.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                txt!!.text = "Now Rate : $rating"
            }
    }
}