package com.jarvanmo.kalproject

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jarvanmo.kal.chooser.OnChooseListener
import com.jarvanmo.kal.chooser.SimpleChooserDialog
import com.jarvanmo.kal.imageviewer.ImageViewerActivity
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
        val list = listOf<String>(
                "http://c.hiphotos.baidu.com/image/h%3D300/sign=a9896c8e9f45d688bc02b4a494c37dab/4b90f603738da97753662396bd51f8198718e3c6.jpg",
                "http://d.hiphotos.baidu.com/image/h%3D300/sign=1c0fb4228835e5dd8f2ca3df46c7a7f5/c83d70cf3bc79f3dab11b040b7a1cd11738b29c9.jpg",
                "http://f.hiphotos.baidu.com/image/h%3D300/sign=0072445ad839b60052ce09b7d9513526/f2deb48f8c5494eee5a348a020f5e0fe98257e81.jpg"
        )
        ImageViewerActivity.startImageViewerWithStrings(this,list)
//        MToast.init(applicationContext)
//        Logger.addLogAdapter(AndroidLogAdapter())
//
//        binding.button.setOnClickListener {
//
//            val a = OnChooseListener { dialogFragment, content ->
//                dialogFragment.dismiss()
//                content.toString().toast()
//            }
//
//            SimpleChooserDialog.showStrings(supportFragmentManager, "A", "B","C", "BD",chooseListener = a)
//        }
    }



}
