package com.example.onetachi.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.onetachi.R
import kotlinx.android.synthetic.main.activity_paper_success.*

class paperSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper_success)

        paperListView.adapter = PaperAdapter(this)
    }

    private class PaperAdapter(context: Context) : BaseAdapter() {
        private val mContext: Context = context
        private var paperList = arrayListOf<Array<String>>(arrayOf("이것이 서류다 1", "발급기관1"), arrayOf("이것이 서류다 2", "발급기관2"))

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.paper_lv_item, viewGroup, false)

            val paperNameView = rowMain.findViewById<TextView>(R.id.paperName)
            paperNameView.text = paperList.get(position)[0]

            val lssuedPaperView = rowMain.findViewById<TextView>(R.id.IssuedPaper)
            lssuedPaperView.text = paperList.get(position)[1]
            return rowMain
        }

        override fun getItem(position: Int): Any {
            return paperList.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return paperList.size
        }
    }
}
