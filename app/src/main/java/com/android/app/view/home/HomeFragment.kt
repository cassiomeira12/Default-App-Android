package com.android.app.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.app.R

class HomeFragment: Fragment() {
    private val TAG = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("cassio", "Home onCreateView")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("cassio", "Home onCreatedView")
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.d("cassio", "Home onResumo")
    }

}