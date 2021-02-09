package kakao.itstudy.supportlibrary

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class Data {
    var title: String? = null
    var image: Drawable? = null
}

class ItemHolder(root: View?) : RecyclerView.ViewHolder(root!!) {
    var textView: TextView
    var imageView: ImageView

    init {
        imageView = itemView.findViewById<ImageView>(R.id.sheet_row_imageView)
        textView = itemView.findViewById<TextView>(R.id.sheet_row_textView)
    }
}

class RecyclerViewAdapter(private val list: List<Data>) :
    RecyclerView.Adapter<ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.sheet_row, parent, false)
        return ItemHolder(root)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val vo = list[position]
        holder.textView.text = vo.title
        holder.imageView.setImageDrawable(vo.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class BottomSheetActivity : AppCompatActivity() {

    var persistentBottomSheet: BottomSheetBehavior<View>? = null
    var modalBottomSheet: BottomSheetDialog? = null

    private fun createDialog() {
        val list: MutableList<Data> = ArrayList()
        var vo = Data()
        vo.title = "Keep"
        vo.image = ResourcesCompat.getDrawable(resources, R.drawable.ic_1, null)
        list.add(vo)
        vo = Data()
        vo.title = "Inbox"
        vo.image = ResourcesCompat.getDrawable(resources, R.drawable.ic_2, null)
        list.add(vo)
        vo = Data()
        vo.title = "Messanger"
        vo.image = ResourcesCompat.getDrawable(resources, R.drawable.ic_3, null)
        list.add(vo)
        vo = Data()
        vo.title = "Google+"
        vo.image = ResourcesCompat.getDrawable(resources, R.drawable.ic_4, null)
        list.add(vo)
        val adapter = RecyclerViewAdapter(list)
        val view: View = layoutInflater.inflate(R.layout.modal_sheet, null)
        val recyclerView =
            view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        modalBottomSheet = BottomSheetDialog(this)
        modalBottomSheet!!.setContentView(view)
        modalBottomSheet!!.show()
    }

    private fun initPeristentBottomSheet() {
        val coordinator = findViewById<CoordinatorLayout>(R.id.coordinator)
        val bottomSheet =
            coordinator.findViewById<View>(R.id.bottom_sheet)
        persistentBottomSheet =
            BottomSheetBehavior.from(
                bottomSheet
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            createDialog()
        }
        initPeristentBottomSheet();

    }
}