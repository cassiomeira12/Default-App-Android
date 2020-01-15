package com.android.app.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.android.app.R
import com.android.app.view.adapter.Adapter

class SearchComponent(var context: Context, var searchView : View, var adapter: Adapter<Any>): View.OnClickListener, TextWatcher {
    var imgSearch: ImageView
    var edtSearch: EditText
    var imgClear: ImageView

    init {
        imgSearch = searchView.findViewById(R.id.imgSearch)
        edtSearch = searchView.findViewById(R.id.edtSearch)
        imgClear = searchView.findViewById(R.id.imgClear)

        imgClear.visibility = View.GONE

        edtSearch.setOnClickListener(this)
        imgClear.setOnClickListener(this)

        edtSearch.addTextChangedListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.getId()) {
            searchView.getId() -> {
                imgSearch.visibility = View.GONE
                edtSearch.requestFocus()
            }
            imgClear.getId() -> {
                edtSearch.getText().clear()
                edtSearch.requestFocus()
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.d("cassio", "before")
        Log.d("cassio", s.toString())
        Log.d("cassio", "start $start")
        Log.d("cassio", "count $count")
        Log.d("cassio", "after $after")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("cassio", "changed")
        Log.d("cassio", s.toString())
        Log.d("cassio", "start $start")
        Log.d("cassio", "before $before")
        Log.d("cassio", "count $count")

        if (count > 0) {
            imgClear.visibility = View.VISIBLE
        } else {
            imgClear.visibility = View.GONE
        }
    }

    override fun afterTextChanged(s: Editable?) {
        Log.d("cassio", "after")
        Log.d("cassio", s.toString())
        adapter!!.search(s.toString())
    }

}