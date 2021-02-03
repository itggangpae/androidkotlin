package kakao.itstudy.network

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_insert.*
import kotlinx.android.synthetic.main.activity_member_join.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MemberJoinActivity : AppCompatActivity() {
    //회원 가입 여부를 출력하는 핸들러
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            val result = message.obj as String
            when (message.what) {
                0 -> Toast.makeText(this@MemberJoinActivity, result, Toast.LENGTH_SHORT).show()
                1 -> {
                    Toast.makeText(this@MemberJoinActivity, result, Toast.LENGTH_SHORT).show()
                    //키보드 관리 객체 가져오기
                    val imm: InputMethodManager = getSystemService(
                        Context.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                    imm.hideSoftInputFromWindow(emailinput.windowToken, 0)
                    imm.hideSoftInputFromWindow(pwinput.windowToken, 0)
                    imm.hideSoftInputFromWindow(nicknameinput.windowToken, 0)
                    emailinput.setText("")
                    pwinput.setText("")
                    nicknameinput.setText("")
                }
            }
        }
    }

    //회원가입 요청을 전송하는 스레드
    inner class ThreadEx : Thread() {
        var json: String? = null
        override fun run() {
            try {
                //다운로드 받을 주소 생성
                val url = URL("http://cyberadam.cafe24.com/member/join")
                //URL에 연결
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection

                //파일을 제외한 파라미터 만들기
                val data = arrayOf(
                    emailinput.text.toString(),
                    pwinput.text.toString(),
                    nicknameinput.text.toString()
                )
                val dataName =
                    arrayOf("email", "pw", "nickname")
                // boundary생성, 여기서는 고정값이지만 되도록이면 실행할때마다 다른값을 할당
                val lineEnd = "\r\n"
                val boundary: String = UUID.randomUUID().toString()
                con.setRequestMethod("POST")
                con.setReadTimeout(10000)
                con.setConnectTimeout(10000)
                con.setDoOutput(true)
                con.setDoInput(true)
                con.setUseCaches(false)

                //파일 첨부가 있는 경우 생성
                con.setRequestProperty("ENCTYPE", "multipart/form-data")
                con.setRequestProperty("Content-Type", "multipart/form-data;boundary=$boundary")
                val delimiter = "--$boundary$lineEnd" // --androidupload\r\n
                val postDataBuilder = StringBuffer()

                //파라미터를 하나로 만들기
                var i = 0
                while (i < data.size) {
                    postDataBuilder.append(delimiter)
                    postDataBuilder.append(
                        "Content-Disposition: form-data; name=\"" + dataName[i] + "\"" + lineEnd + lineEnd + data[i] + lineEnd
                    )
                    i = i + 1
                }
                // 파일 파라미터 생성
                val fileName = "musa.png"
                //String fileName = null
                if (fileName != null) {
                    postDataBuilder.append(delimiter)
                    postDataBuilder.append("Content-Disposition: form-data; name=\"profile\";filename=\"$fileName\"$lineEnd")
                }
//파라미터 전송
                val ds = DataOutputStream(con.getOutputStream())
                ds.write(postDataBuilder.toString().toByteArray())
                if (fileName != null) {
                    //파일 업로드
                    ds.writeBytes(lineEnd)
                    val fres: InputStream = resources.openRawResource(R.raw.musa)
                    val buffer = ByteArray(fres.available())
                    var length = -1

                    while (fres.read(buffer).also({ length = it }) != -1) {
                        ds.write(buffer, 0, length)
                    }

                    ds.writeBytes(lineEnd)
                    ds.writeBytes(lineEnd)
                    ds.writeBytes("--$boundary--$lineEnd") // requestbody end

                    fres.close()
                } else {
                    ds.writeBytes(lineEnd)
                    ds.writeBytes("--$boundary--$lineEnd") // requestbody end
                }
                ds.flush()
                ds.close()

                //문자열을 다운로드 받기 위한 스트림을 생성
                val br = BufferedReader(InputStreamReader(con.getInputStream()))
                val sb = StringBuilder()
                //문자열을 읽어서 저장
                while (true) {
                    val line: String = br.readLine() ?: break
                    sb.append(
                        """
                        $line
                        """.trimIndent()
                    )
                }
                //사용한 스트림과 연결 해제
                br.close()
                con.disconnect()
                json = sb.toString()
                Log.e("다운로드 받은 문자열", json.toString())
            } catch (e: Exception) {
                Log.e("회원가입 예외", e.localizedMessage)
                e.printStackTrace()
                val message = Message()
                message.obj = "회원가입 에러"
                message.what = 0
                handler.sendMessage(message)
            }
            if (json != null) {
                try {
                    val `object` = JSONObject(json)
                    Log.e("데이터", `object`.toString())
                    val result: Boolean = `object`.getBoolean("result")
                    val message = Message()
                    if (result == true) {
                        message.obj = "회원가입 성공"
                        message.what = 1
                    } else {
                        val emailcheck: Boolean = `object`.getBoolean("emailcheck")
                        if (emailcheck == false) {
                            message.obj = "이메일이 사용 중이므로 회원가입 실패"
                        } else {
                            message.obj = "닉네임이 사용 중이이므로 회원가입 실패"
                        }
                        message.what = 0
                    }
                    handler.sendMessage(message)
                } catch (e: Exception) {
                    Log.e("파싱 예외", e.localizedMessage)
                    val message = Message()
                    message.obj = "파싱 에러"
                    message.what = 0
                    handler.sendMessage(message)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_join)

        join.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val errorMessage = Message()
                val email = emailinput.text.toString().trim()
                val pw = pwinput.text.toString().trim()
                val nickname = nicknameinput.text.toString().trim()

                if (email.length == 0) {
                    errorMessage.obj = "email은 비어있을 수 없습니다."
                    handler.sendMessage(errorMessage)
                    return
                } else {
                    val regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
                    val p: Pattern = Pattern.compile(regex)
                    val m: Matcher = p.matcher(email)
                    if (m.matches() === false) {
                        errorMessage.obj = "email 형식과 일치하지 않습니다."
                        errorMessage.what = 0
                        handler.sendMessage(errorMessage)
                        return
                    }
                }

                if (pw.length == 0) {
                    errorMessage.obj = "비밀번호는 비어있을 수 없습니다."
                    errorMessage.what = 0
                    handler.sendMessage(errorMessage)
                    return
                } else {
                    val regex =
                        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}"
                    val p: Pattern = Pattern.compile(regex)
                    val m: Matcher = p.matcher(pw)
                    if (m.matches() === false) {
                        errorMessage.obj = "비밀번호는 영문 대소문자 1개 이상 특수문자 1개 숫자 1개 이상으로 만들어져야 합니다. "
                        errorMessage.what = 0
                        handler.sendMessage(errorMessage)
                        return
                    }
                }

                if (nickname.length < 2) {
                    errorMessage.obj = "별명은 2자 이상이어야 합니다."
                    errorMessage.what = 0
                    handler.sendMessage(errorMessage)
                    return
                } else {
                    val regex = "[0-9]|[a-z]|[A-Z]|[가-힣]"
                    var i = 0
                    while (i < nickname.length) {
                        val ch = nickname[i].toString() + ""
                        val p: Pattern = Pattern.compile(regex)
                        val m: Matcher = p.matcher(ch)
                        if (m.matches() === false) {
                            errorMessage.obj = "별명은 영문 숫자 한글만 사용해야 합니다."
                            errorMessage.what = 0
                            handler.sendMessage(errorMessage)
                            return
                        }
                        i++
                    }
                }
                Log.e("유효성 검사", "통과")
                ThreadEx().start()
            }
        })
    }
}