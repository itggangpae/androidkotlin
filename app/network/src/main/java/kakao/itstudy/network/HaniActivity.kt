package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_hani.*
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class HaniActivity : AppCompatActivity() {
    var xml: String? = null
    var sBuffer: StringBuffer? = null

    fun click(v: View?) {
        val th: HaniActivity.ThreadEx = ThreadEx()
        th.start()
    }

    //핸들러 - 파싱한 결과를 출력하는 핸들러 작성
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val article = msg.obj as String
            result.text = article
        }
    }

    protected inner class ThreadEx : Thread() {
        override fun run() {
            sBuffer = StringBuffer()
            try {
                val urlAddr = "http://www.hani.co.kr/rss/"
                val url = URL(urlAddr)
                val conn =
                    url.openConnection() as HttpURLConnection
                if (conn != null) {
                    conn.connectTimeout = 20000
                    conn.useCaches = false
                    if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                        val isr = InputStreamReader(
                            conn.inputStream
                        )
                        val br = BufferedReader(isr)
                        while (true) {
                            val line = br.readLine() ?: break
                            sBuffer!!.append(line)
                        }
                        br.close()
                        conn.disconnect()
                    }
                }
                xml = sBuffer.toString()
            } catch (e: Exception) {
                Log.e("다운로드 중 에러 발생", e.message!!)
            }

            try {
                Log.e("xml", xml!!)
                if (xml != null) {
                    val factory =
                        DocumentBuilderFactory.newInstance()
                    val documentBuilder =
                        factory.newDocumentBuilder()
                    val `is`: InputStream = ByteArrayInputStream(xml!!.toByteArray())
                    val doc = documentBuilder.parse(`is`)
                    val element = doc.documentElement
                    val titles = element.getElementsByTagName("title")
                    val links = element.getElementsByTagName("link")
                    val n = titles.length
                    sBuffer = StringBuffer()
                    for (i in 1 until n) {
                        val title = titles.item(i)
                        val text = title.firstChild
                        val titleValue = text.nodeValue
                        val link = links.item(i)
                        val linktext = link.firstChild
                        val linkValue = linktext.nodeValue
                        sBuffer!!.append("$titleValue:$linkValue\n\n")
                    }
                    val message = Message()
                    message.obj = sBuffer.toString()
                    mHandler.sendMessage(message)
                }
            } catch (e: Exception) {
                Log.e("파싱 중 에러 발생", e.message!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hani)
    }
}