package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dom.*
import java.io.ByteArrayInputStream
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class DomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dom)
    }

    fun click(v: View) {
        val xml = """<?xml version="1.0" encoding="utf-8"?>
<baseball><team name="Samsung">류중일</team><team name="Nexson">염경엽</team><team name="KIA">김기태</team></baseball>"""
        try {
            val factory =
                DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val istream: InputStream =
                ByteArrayInputStream(xml.toByteArray(charset("utf-8")))
            val doc = builder.parse(istream)
            val baseball = doc.documentElement
            val items = baseball.getElementsByTagName("team")
            var Result = ""
            for (i in 0 until items.length) {
                val item = items.item(i)
                val text = item.firstChild
                val directorName = text.nodeValue
                Result += "$directorName : "
                val Attrs = item.attributes
                for (j in 0 until Attrs.length) {
                    val attr = Attrs.item(j)
                    Result += attr.nodeName + " = " + attr.nodeValue + "  "
                }
                Result += "\n"
            }
            result.text = "프로야구\n$Result"
        } catch (e: Exception) {
            Toast.makeText(v.context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}