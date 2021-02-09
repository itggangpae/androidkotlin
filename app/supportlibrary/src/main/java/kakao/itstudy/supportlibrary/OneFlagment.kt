package kakao.itstudy.supportlibrary

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.ListFragment

class OneFragment : ListFragment() {
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datas =
            arrayOf("Encapsulation", "Inheritance", "Polymorphism")

        val aa = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, datas)
        setListAdapter(aa)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val toat = Toast.makeText(
            getActivity(),
            l.getAdapter().getItem(position) as String,
            Toast.LENGTH_SHORT
        )
        toat.show()
    }
}