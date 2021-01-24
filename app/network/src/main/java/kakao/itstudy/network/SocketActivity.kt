package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_socket.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class SocketActivity : AppCompatActivity() {
    var mes = ""

    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.e("메시지", mes)
            textView.append(mes)
        }
    }

    inner class ConnectThread(var hostname: String) : Thread() {
        override fun run() {
            var sock: Socket? = null
            var outstream: ObjectOutputStream? = null
            var instream: ObjectInputStream? = null

            val port = 11001
            sock = Socket(hostname, port)
            outstream = ObjectOutputStream(sock.getOutputStream())
            outstream.writeObject(editText.text.toString())
            outstream.flush()
            instream = ObjectInputStream(
                sock.getInputStream()
            )
            mes = instream.readObject() as String + "\n"
            mHandler.sendEmptyMessage(0)


            if (outstream != null) outstream.close()
            if (instream != null) instream.close()
            if (sock != null) sock.close()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)

        // 버튼 이벤트 처리
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val addr = "192.168.10.106"
                // 네트워킹을 사용하는 경우 스레드를 이용해야 합니다.
                val thread = ConnectThread(addr)
                thread.start()
            }
        })
    }
}