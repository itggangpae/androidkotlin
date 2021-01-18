package kakao.itstudy.varietyview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView?.webViewClient = WebViewClient()
        var settings = webView?.settings
        settings!!.javaScriptEnabled = true
        settings!!.builtInZoomControls = true
        webView?.loadUrl("http://www.google.com")
        val handler =
            View.OnClickListener { v ->
                when (v.id) {
                    R.id.btngo -> {
                        val url: String
                        url = addr.text.toString()
                        webView?.loadUrl(url)
                    }
                    R.id.btnback -> if (webView?.canGoBack()!!) {
                        webView?.goBack()
                    }
                    R.id.btnforward -> if (webView?.canGoForward()!!) {
                        webView?.goForward()
                    }
                    R.id.btnlocal -> webView?.loadUrl("file:///android_asset/test.html")
                }
            }

        btngo?.setOnClickListener(handler)
        btnback?.setOnClickListener(handler)
        btnforward?.setOnClickListener(handler)
        btnlocal?.setOnClickListener(handler)

    }
}