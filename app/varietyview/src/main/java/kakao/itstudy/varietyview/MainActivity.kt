package kakao.itstudy.varietyview

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spanView.movementMethod = ScrollingMovementMethod()
        val data = "대한민국 \n img \n 내가 살아온 자랑스런 나의 조국"
        val builder = SpannableStringBuilder(data)
        var start = data.indexOf("img")
        if (start > -1) {
            val end = start + "img".length
            val dr =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.korea,
                    null
                )
            dr!!.setBounds(0, 0, dr!!.intrinsicWidth, dr!!.intrinsicHeight)
            val span = ImageSpan(dr!!)
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        start = data.indexOf("대한민국")
        if (start > -1) {
            val end = start + "대한민국".length
            val styleSpan = StyleSpan(Typeface.BOLD)
            val sizeSpan = RelativeSizeSpan(2.0f)
            builder.setSpan(styleSpan, start, end + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(sizeSpan, start, end + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        spanView.text = builder

    }
}
