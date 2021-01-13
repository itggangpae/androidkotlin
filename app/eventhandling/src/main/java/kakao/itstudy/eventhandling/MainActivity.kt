package kakao.itstudy.eventhandling

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    //화면을 처음 터치할 때의 좌표를 저장하기 위한 변수
    var initX = 0f

    //백버튼을 누른 시간을 저장하기 위한 변수
    var initTime: Long = 0

    //Toast를 출력하기 위한 메소드
    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    //화면을 터치했을 때 호출되는 메소드
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            initX = event.rawX
        } else if (event.action == MotionEvent.ACTION_UP) {
            val diffX = initX - event.rawX
            if (diffX > 30) {
                showToast("왼쪽으로 화면을 밀었습니다.")
            } else if (diffX < -30) {
                showToast("오른쪽으로 화면을 밀었습니다.")
            }
        }
        return true
    }

    //키보드를 눌렀을 때 호출되는 메소드
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                showToast("종료하려면 한번 더 누르세요.")
                initTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /*
    inner class MyView(context: Context?) : View(context) {

        override fun onTouchEvent(event: MotionEvent): Boolean {
            super.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_DOWN) {
                Toast.makeText(this@MainActivity, "Touch Event Received",
                    Toast.LENGTH_SHORT).show()
                return true
            }
            return false
        }
     }
     */

    inner class MyView(context: Context?) : View(context), View.OnTouchListener  {
        init{
            setOnTouchListener(this)
        }
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            Snackbar.make(this, "I’m Snackbar!", Snackbar.LENGTH_LONG)
                .setAction("OK"){Toast.makeText(this@MainActivity, "Snack Bar Click",
                    Toast.LENGTH_SHORT).show()}
                .show()
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        val vw = MyView(this)
        setContentView(vw)

        /*
        vw.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    Toast.makeText(
                        this@MainActivity, "터치 이벤트 발생",
                        Toast.LENGTH_SHORT
                    ).show()
                    return true
            }
        })
        */

        /*
        vw.setOnTouchListener({v: View, event: MotionEvent ->
                Toast.makeText(this@MainActivity, "터치 이벤트 발생",
                    Toast.LENGTH_SHORT).show()
                true
        })

        */
    }
}
