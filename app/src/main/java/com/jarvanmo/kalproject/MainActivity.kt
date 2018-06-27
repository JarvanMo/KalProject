package com.jarvanmo.kalproject

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jarvanmo.kal.logD

import com.jarvanmo.kal.recyclerview.QuickAdapter
import com.jarvanmo.kalproject.databinding.ActivityMainBinding
import com.jarvanmo.kalproject.databinding.EmptyViewBinding
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var x = 0
//    val binding:ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
//    val empty = EmptyViewBinding.inflate(getLayoutInflater())
//    val quickAdapter = QuickAdapter<String>(BR.viewModel, R.layout.item_text_view)
//    binding.rc.setEmptyView(empty.root)
//    binding.rc.setAdapter(quickAdapter)
//    quickAdapter.notifyDataSetChanged()
//
//
//    empty.textView2.setOnClickListener {
//        for (i in 0..9) {
//            val s = i.toString() + ""
//            quickAdapter.add(s)
//        }
//        quickAdapter.notifyDataSetChanged()
//    }
//    binding.button.setOnClickListener(View.OnClickListener {
//        if (quickAdapter.itemCount > 50) {
//            quickAdapter.clear()
//            return@OnClickListener
//        }
//
//        val strings = ArrayList<String>()
//        for (i in 0..9) {
//
//            strings.add((++x).toString() + "")
//
//        }
//        quickAdapter.addAll(strings)
//        quickAdapter.notifyDataSetChanged()
//    })

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())
        "weco".logD()
    }

}
