package kakao.itstudy.network

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_text.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TextActivity : AppCompatActivity() {
    var defaultUrl = "https://www.google.com"
    var handler = Handler(Looper.getMainLooper())

    private fun request(urlStr: String): String {
        val output = StringBuilder()
        var result = ""
        try {
            val url = URL(urlStr)
            val conn =
                url.openConnection() as HttpURLConnection
            if (conn != null) {
                conn.connectTimeout = 10000
                conn.useCaches = false
                //conn.setRequestProperty("Accept-Charset", "EUC-KR");
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val br = BufferedReader(
                        InputStreamReader(conn.inputStream)
                    )
                    while (true) {
                        val line = br.readLine() ?: break
                        output.append("""
    $line
    """.trimIndent())
                    }
                    br.close()
                    result = output.toString()
                }
                conn.disconnect()
            }
        } catch (ex: Exception) {
            Log.e("SampleHTTPClient", "Exception in processing response.", ex)
        }
        return result
    }

    protected inner class ConnectThread(var urlStr: String) : Thread() {
        override fun run() {
            try {
                val output = request(urlStr)
                handler.post { txtmsg.setText(output)}
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        inputurl.setText(defaultUrl)
        // 버튼 이벤트 처리
        requestbtn.setOnClickListener {
            val urlStr = inputurl!!.text.toString()
            val thread =
                ConnectThread(urlStr)
            thread.start()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputurl!!.windowToken, 0)
        return super.onTouchEvent(event)
    }

}