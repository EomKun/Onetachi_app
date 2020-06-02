package com.example.onetachi.auth

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.onetachi.R
import com.example.onetachi.data.*
import com.example.onetachi.retrofit.MyRetrofit
import com.example.onetachi.user.loginedActivity
import kotlinx.android.synthetic.main.activity_paper_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Response

class paperListActivity : AppCompatActivity() {
    var paperList :ArrayList<Array<String>>? = arrayListOf()
    //var selectedPaperList : ArrayList<Array<String>> = arrayListOf()
    val myContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper_list)

        val userId = intent.getStringExtra("id").toString()

        // available Paper List request
        MyRetrofit().service.availablePaper(User(userId))?.enqueue(object : retrofit2.Callback<Papers>{
            override fun onFailure(call: Call<Papers>?, t: Throwable) {
                toast("서류를 요청하는데 오류가 발생했습니다")
            }

            override fun onResponse(call: Call<Papers>, response: Response<Papers>) {
                paperList = response.body()?.papers

                userIdView.text = userId

                // ListView add adapter
                paperListView.adapter = PaperAdapter(myContext)

                // List Item Touch Listener
                paperListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    val selectItem = parent.getItemAtPosition(position) as Array<String>
                    if(paperListView.isItemChecked(position)){
                        paperListView.setItemChecked(position, true)
                        view.setBackgroundColor(Color.LTGRAY)
                    } else {
                        paperListView.setItemChecked(position, false)
                        view.setBackgroundColor(Color.WHITE)
                    }


                    val selectedArray = paperListView.checkedItemPositions
                    toast(selectItem[0] + " " + selectedArray)
                }
            }
        })

        // Paper Request Button Listener
        paperReqButton.setOnClickListener{
            startActivity<loginedActivity>()
        }
    }

    inner class PaperAdapter(context: Context) : BaseAdapter() {
        private val mContext: Context = context

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.paper_lv_item, viewGroup, false)

            // Paper Listing
            if (paperList == null)
            {
                rowMain.findViewById<TextView>(R.id.paperName).text =
                    "발급 가능한 서류가 없습니다"
            } else {
                val paperNameView = rowMain.findViewById<TextView>(R.id.paperName)
                paperNameView.text = paperList!!.get(position)[0]

                val lssuedPaperView = rowMain.findViewById<TextView>(R.id.IssuedPaper)
                lssuedPaperView.text = paperList!!.get(position)[1]
            }

            return rowMain
        }

        override fun getItem(position: Int): Any {
            return paperList!!.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return paperList!!.size
        }
    }
}
