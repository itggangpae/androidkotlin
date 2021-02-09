package kakao.itstudy.supportlibrary

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerActivity : AppCompatActivity() {
    internal class HeaderViewHolder(headerView: View?) :
        RecyclerView.ViewHolder(headerView!!)

    internal class FooterViewHolder(footerView: View?) :
        RecyclerView.ViewHolder(footerView!!)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title: TextView
        init {
            title = itemView.findViewById(android.R.id.text1)
        }

        fun onBind(data: String) {
            title.setText(data.toString())
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Toast.makeText(title.context, title.text, Toast.LENGTH_SHORT)
                .show()
            Log.e("메시지", title.text.toString())
        }
    }

    class MyAdapter(var list: List<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
            if (i == 0) {
                val view =
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.header, viewGroup, false)
                return HeaderViewHolder(view)
            } else if (i == 2) {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.footer, viewGroup, false);
                return FooterViewHolder(view);
            }else {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(android.R.layout.simple_list_item_1, viewGroup, false)
                return MyViewHolder(view)
            }
        }

        override fun getItemCount(): Int {
            return list.size + 2
        }
        override fun onBindViewHolder(myViewHolder: RecyclerView.ViewHolder, i: Int) {
            if (myViewHolder is HeaderViewHolder) {
                val headerViewHolder = myViewHolder as HeaderViewHolder
            } else if (myViewHolder is FooterViewHolder) {
                val footerViewHolder = myViewHolder as FooterViewHolder
            } else {
                // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
                val itemViewHolder: MyViewHolder = myViewHolder as MyViewHolder
                itemViewHolder.onBind(list.get(i - 1))
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position == 0)
                return 0;
            else if (position == list.size + 1)
                return 2;
            else
                return 1;
        }
    }

    inner class MyItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state!!)
            val index: Int = parent.getChildAdapterPosition(view) + 1
            if (index % 3 == 0) {
                outRect.set(20, 20, 20, 60)
            } else {
                outRect.set(20, 20, 20, 20)
            }
            view.setBackgroundColor(-0x131617)
            ViewCompat.setElevation(view, 20.0f)
        }
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
            val width: Int = parent.getWidth()
            val height: Int = parent.getHeight()
            val dr: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.img, null)
            val drWidth: Int = dr!!.getIntrinsicWidth()
            val drHeight: Int = dr.getIntrinsicHeight()
            val left  = width / 2 - drWidth / 2
            val top = height / 2 - drHeight / 2
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img), left.toFloat(), top.toFloat(), null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val list: MutableList<String> = ArrayList()
        list.add("태연")
        list.add("제시카")
        list.add("유리")
        list.add("티파니")
        list.add("수영")
        list.add("써니")
        list.add("효연")
        list.add("윤아")
        list.add("서현")
        list.add("태연")
        list.add("제시카")
        list.add("유리")
        list.add("티파니")
        list.add("수영")
        list.add("써니")
        list.add("효연")
        list.add("윤아")
        list.add("서현")
        list.add("태연")
        list.add("제시카")
        list.add("유리")
        list.add("티파니")
        list.add("수영")
        list.add("써니")
        list.add("효연")
        list.add("윤아")
        list.add("서현")

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler. setLayoutManager(LinearLayoutManager(this))
        recycler.adapter = MyAdapter(list)
        recycler.addItemDecoration(MyItemDecoration())

    }
}