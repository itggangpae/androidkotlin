package kakao.itstudy.multimedia



import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

//컴파일 할 때 결정되는 상수 : 숫자형과 String 만 가능
private const val ARG_URI = "uri"

class PhotoFragment : Fragment() {
    //지연 생성
    private lateinit var uri: Uri

    //Fragment를 생성할 때 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //번들에 저장된 값을 가져와서 uri 에 설정
        arguments?.getParcelable<Uri>(ARG_URI)?.let {
            uri = it
        }
    }

    //화면을 만드는 함수
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //uri 값을 읽어서 파일에 대한 정보를 가져오고 파일을 비트맵으로 가져온 후 imageView에 출력
        val descriptor = requireContext().contentResolver.openFileDescriptor(uri, "r")
        descriptor?.use {
            val bitmap = BitmapFactory.decodeFileDescriptor(descriptor.fileDescriptor)
            val imageView = activity!!.findViewById<ImageView>(R.id.imageView)
            Glide.with(this).load(bitmap).into(imageView)
        }
    }

    //static을 만드는 것으로 newInstance 함수를 호출해서 Fragment를 생성하고 bundle에 uri를 저장하는 역할
    companion object {
        @JvmStatic
        fun newInstance(uri: Uri) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_URI, uri)
                }
            }
    }
}
