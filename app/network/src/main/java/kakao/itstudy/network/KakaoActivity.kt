package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_kakao.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class KakaoActivity : AppCompatActivity() {
    //다운로드 받은 문자열을 저장할 변수
    var json: String? = null
    var sb: StringBuilder? = null


    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val result = msg.obj as String
            list.text = result
        }
    }

    inner class ThreadEx : Thread() {
        override fun run() {
            try {
                var urlAddr: String? =
                    "https://dapi.kakao.com/v3/search/book?target=title&query="
                val title = "삼국지"
                urlAddr += URLEncoder.encode(title, "utf-8")
                val url = URL(urlAddr)
                val conn =
                    url.openConnection() as HttpURLConnection
                conn.setRequestProperty("Authorization", "KakaoAK ae6b2875ee4452804ed4e01890078a7e")
                sb = StringBuilder()
                if (conn != null) {
                    conn.connectTimeout = 20000
                    conn.useCaches = false
                    if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                        val isr =
                            InputStreamReader(conn.inputStream)
                        val br = BufferedReader(isr)
                        while (true) {
                            val line = br.readLine() ?: break
                            sb!!.append(line)
                        }
                        br.close()
                        conn.disconnect()
                    }
                }
                json = sb.toString()
            } catch (e: Exception) {
                Log.e("다운로드 중 에러 발생", e.message!!)
            }
            try {
                sb = StringBuilder()
                val obj = JSONObject(json)
                val documents = obj.getJSONArray("documents")
                for (i in 0 until documents.length()) {
                    val book = documents.getJSONObject(i)
                    sb!!.append(
                        """
    제목:${book.getString("title")},가격:${book.getString("price")}
    
    """.trimIndent()
                    )
                }
                val message = Message()
                message.obj = sb.toString()
                mHandler.sendMessage(message)
            } catch (e: Exception) {
                Log.e("파싱 중 에러 발생", e.message!!)
            }
        }
    }

    fun click(v: View?) {
        val th: KakaoActivity.ThreadEx = ThreadEx()
        th.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao)
    }
}