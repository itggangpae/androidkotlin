package kakao.itstudy.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_image.*
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class ImageActivity : AppCompatActivity() {
    //버튼의 클릭 이벤트
    fun click(v: View) {
        val imageurl: String
        when (v.id) {
            R.id.btndraw -> {
                val th =
                    DownThread1("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSARlFjrDFC6FElhTVpMln0kA3cLkeWUHF5Q05yDvoG_MU6hM3_Zg")
                th.start()
            }
            R.id.btndown -> {
                imageurl = "http://www.onlifezone.com/files/attach/images/962811/376/321/005/2.jpg"
                val idx = imageurl.lastIndexOf('/')
                val localimage = imageurl.substring(idx + 1)
                var path =
                    Environment.getDataDirectory().absolutePath
                path += "/data/kakao.itstudy.network/files/$localimage"
                if (File(path).exists()) {
                    Toast.makeText(this, "bitmap is exist", Toast.LENGTH_LONG).show()
                    result.setImageBitmap(BitmapFactory.decodeFile(path))
                } else {
                    Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_LONG).show()
                    DownThread2(imageurl, localimage).start()
                }
            }
        }
    }

    //이미지를 서버에서 가져와서 Bitmap으로 생성하는 스레드
    internal inner class DownThread1(var mAddr: String) : Thread() {
        override fun run() {
            try {
                val `is` = URL(mAddr).openStream()
                val bit = BitmapFactory.decodeStream(`is`)
                `is`.close()
                val message = mAfterDown.obtainMessage()
                message.obj = bit
                mAfterDown.sendMessage(message)
            } catch (e: Exception) {
            }
        }
    }
    //이미지를 출력하는 핸들러
    var mAfterDown: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val bit = msg.obj as Bitmap
            if (bit == null) {
                Toast.makeText(this@ImageActivity, "bitmap is null", Toast.LENGTH_LONG).show()
            } else {
                result.setImageBitmap(bit)
            }
        }
    }

    //이미지를 다운로드 받아서 앱에 저장하는 스레드
    internal inner class DownThread2(var mAddr: String, var mFile: String?) :
        Thread() {
        override fun run() {
            val imageurl: URL
            var read: Int
            try {
                imageurl = URL(mAddr)
                val conn =
                    imageurl.openConnection() as HttpURLConnection
                val len = conn.contentLength
                val raster = ByteArray(len)
                val `is` = conn.inputStream
                val fos = openFileOutput(mFile, 0)
                while (true) {
                    read = `is`.read(raster)
                    if (read <= 0) {
                        break
                    }
                    fos.write(raster, 0, read)
                }
                `is`.close()
                fos.close()
                conn.disconnect()
            } catch (e: Exception) {
                mFile = null
            }
            val message = mAfterDown.obtainMessage()
            message.obj = mFile
            mAfterDown1.sendMessage(message)
        }

        init {
            Log.e("mAddr", mAddr)
        }
    }

    //이미지 파일이 있으면 있는 이미지를 출력하고 없으면 다운로드 받는 스레드를 실행하는 스레드
    var mAfterDown1: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.obj != null) {
                var path =
                    Environment.getDataDirectory().absolutePath
                path += "/data/kakao.itstudy.network/" + msg.obj as String
                result.setImageBitmap(BitmapFactory.decodeFile(path))
            } else {
                Toast.makeText(this@ImageActivity, "File not found", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
    }
}