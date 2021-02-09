package kakao.itstudy.supportlibrary

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

class TwoFragment : DialogFragment() {
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(getActivity())
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setTitle("DialogFragment")
        builder.setMessage("DialogFragment 내용이 잘 보이지요")
        builder.setPositiveButton("OK", null)
        return builder.create()
    }
}