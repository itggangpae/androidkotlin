package kakao.itstudy.adpterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class LinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)

        val webview = findViewById<WebView>(R.id.webview)
        webview.webViewClient = WebViewClient()
        var settings = webview.settings
        settings!!.javaScriptEnabled = true
        settings!!.builtInZoomControls = true

        val intent = getIntent()
        val link = intent.getStringExtra("link")
        webview.loadUrl(link!!)


    }
}