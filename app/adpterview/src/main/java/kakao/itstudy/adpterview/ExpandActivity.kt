package kakao.itstudy.adpterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ListAdapter
import android.widget.SimpleExpandableListAdapter

class ExpandActivity : AppCompatActivity() {
    var list: ExpandableListView? = null
    var main = mutableListOf<String>(
        "BackEnd", "FrontEnd", "Database"
    )

    var sub = mutableListOf<MutableList<String>>(
        mutableListOf(
            "Java",
            "Node.js",
            "PHP",
            "Python"
        ),
        mutableListOf("HTML", "CSS", "JavaScript", "jQuery", "react", "vue", "angular"),
        mutableListOf("Oracle", "MySQL", "MSSQL", "MongoDB")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand)

        list = findViewById<ExpandableListView>(R.id.list)
        val mainData: MutableList<Map<String, String>> =
            mutableListOf()
        val subData: MutableList<List<Map<String, String>>> =
            mutableListOf()
        for (i in 0 until main.size) {
            val mainMap: MutableMap<String, String> =
                HashMap()
            mainMap["main"] = main[i]
            mainData.add(mainMap)
            val children: MutableList<Map<String, String>> =
                ArrayList()
            for (j in 0 until sub[i].size) {
                val subMap: MutableMap<String, String> =
                    HashMap()
                subMap["sub"] = sub[i][j]
                children.add(subMap)
            }
            subData.add(children)
        }
        val adapter: ExpandableListAdapter = SimpleExpandableListAdapter(
            this,
            mainData,
            android.R.layout.simple_expandable_list_item_1,
            arrayOf("main"),
            intArrayOf(android.R.id.text1),
            subData,
            android.R.layout.simple_expandable_list_item_1,
            arrayOf("sub"),
            intArrayOf(android.R.id.text1)
        )
        list!!.setAdapter(adapter)
    }
}