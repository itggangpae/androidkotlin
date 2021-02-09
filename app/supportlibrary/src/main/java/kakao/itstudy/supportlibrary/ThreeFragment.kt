package kakao.itstudy.supportlibrary

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

class ThreeFragment : Fragment() {
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_three, container, false)
    }
}