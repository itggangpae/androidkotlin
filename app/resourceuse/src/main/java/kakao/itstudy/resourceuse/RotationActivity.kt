package kakao.itstudy.resourceuse

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class RotationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotation)
    }

    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //회전 방향 확인
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("현재 방향", "landscape")
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("현재 방향", "portrait")
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
    */

    override fun onConfigurationChanged(newConfig: Configuration) {
        setContentView(R.layout.activity_rotation)
        super.onConfigurationChanged(newConfig!!)
    }

}