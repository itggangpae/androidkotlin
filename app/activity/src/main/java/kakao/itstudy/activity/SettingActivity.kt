package kakao.itstudy.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toggleBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val manager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
        })

    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onResume() {
        super.onResume()
        showToast("onResume.....")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            if (isInMultiWindowMode) {
                showToast("onResume....isInMultiWindowMode.. yes ")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        showToast("onPause......")
    }
    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, configuration: Configuration) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, configuration)
        showToast("onMultiWindowModeChanged.....$isInMultiWindowMode")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Log.e("방향","portrait.....")
        } else {
            Log.e("방향","landscape.....")
        }
    }

}