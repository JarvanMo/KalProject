package com.jarvanmo.kalproject

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jarvanmo.kal.chooser.OnChooseListener
import com.jarvanmo.kal.chooser.SimpleChooserDialog
import com.jarvanmo.kal.toast
import com.jarvanmo.kal.util.MToast
import com.jarvanmo.kalproject.databinding.ActivityMainBinding
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

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
//    empty.textView2.setOnClickListener {n
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
        MToast.init(applicationContext)
        Logger.addLogAdapter(AndroidLogAdapter())

        binding.button.setOnClickListener {

            val a = OnChooseListener { dialogFragment, content ->
                dialogFragment.dismiss()
                content.toString().toast()
            }

            SimpleChooserDialog.showStrings(supportFragmentManager, "A", "B","C", "BD",chooseListener = a)
        }
    }



}
