package kakao.itstudy.varietyview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hybrid.*

class HybridActivity : AppCompatActivity() {

    class WebCustomClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            return false
        }
    }

    class AndroidJavaScriptInterface(aContext: Context?) {
        private var mContext: Context? = null
        private val handler: Handler = Handler()

        @JavascriptInterface
        fun showToastMessage(aMessage: String?) {
            handler.post(Runnable {
                Toast.makeText(
                    mContext,
                    aMessage,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }

        init {
            mContext = aContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hybrid)


        webview.setWebViewClient(WebCustomClient())
        webview.getSettings()?.javaScriptEnabled = true
        webview.addJavascriptInterface(AndroidJavaScriptInterface(this), "MYApp")
        webview.loadUrl("http://192.168.10.106:8080/javaandroidweb//")

        sendmsg.setOnClickListener {

            val sendmessage = sendtxt.text.toString()
            Toast.makeText(this, sendmessage, Toast.LENGTH_LONG).show()
            webview.loadUrl("javascript:showDisplayMessage('$sendmessage')")
        }
    }
}