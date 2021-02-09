package kakao.itstudy.supportlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CoordinatorActivity : AppCompatActivity() {
    var coordinatorLayout: CoordinatorLayout? = null

    private class MyAdapter(private val list: List<String>) :
        RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(android.R.layout.simple_list_item_1, viewGroup, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
            val text = list[position]
            viewHolder.title.text = text
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
    private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(android.R.id.text1)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val list: MutableList<String> = ArrayList()
        for (i in 0..19) {
            list.add("Item=$i")
        }

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.setLayoutManager(LinearLayoutManager(this))
        recycler.setAdapter(MyAdapter(list))

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val coordinator = findViewById<CoordinatorLayout>(R.id.coordinator)
        fab.setOnClickListener{
            Snackbar.make(coordinator, "I am SnackBar", Snackbar.LENGTH_LONG)
                .setAction("MoreAtion", View.OnClickListener {
                    //.......................
                })
                .show()
        }
    }
}