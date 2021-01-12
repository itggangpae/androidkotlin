package kakao.itstudy.userinterface

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_edit_text.*

class EditTextActivity : AppCompatActivity() {
    //anonymous class를 이용한 TextWatcher 인스턴스 생성
    private var watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            text.setText("echo:$s")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)

        //TextChanged 이벤트 리스너 등록
        edit.addTextChangedListener(watcher);

        limit.setFilters(
            arrayOf<InputFilter>(
                InputFilter.LengthFilter(3)
            )
        )

        //키보드 관리 객체 가져오기
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //show를 눌렀을 때 키보드를 화면에 출력하고 키보드를 누를 때 edit에 입력되도록 설정
        show.setOnClickListener( { imm.showSoftInput(edit, 0)})
        //hide를 눌렀을 때 edit에 입력되도록 설정된 키보드를 화면에서 제거
        hide.setOnClickListener( {imm.hideSoftInputFromWindow(edit.getWindowToken(), 0) })
    }
}