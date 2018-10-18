package com.jarvanmo.kal.chooser

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.jarvanmo.kal.R
import com.jarvanmo.kal.databinding.DialogSimpleChooserBinding
import com.jarvanmo.kal.databinding.ItemSimpleChooserBinding
import com.jarvanmo.kal.recyclerview.RecyclerViewAdapter

/**
 * Created by mo on 17-6-19.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */
class SimpleChooserDialog : DialogFragment() {


    companion object {
        @JvmStatic
        private  val keyIsString = "extraType"

        @JvmStatic
        private val keyData = "keyData"

        @JvmStatic
        private  val simpleChooser = "simpleChooser"

        @JvmStatic
        fun newInstanceOfParcelables(items: ArrayList<Parcelable>): SimpleChooserDialog {

            val args = Bundle()
            args.putBoolean(keyIsString, false)
            args.putParcelableArrayList(keyData, items)
            val fragment = SimpleChooserDialog()
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newInstanceOfParcelables(vararg items: Parcelable): SimpleChooserDialog {

            val list:ArrayList<Parcelable>  = ArrayList()
            items.flatMapTo(list) { arrayListOf(it)}

            val args = Bundle()
            args.putBoolean(keyIsString, false)
            args.putParcelableArrayList(keyData, list)
            val fragment = SimpleChooserDialog()
            fragment.arguments = args
            return fragment
        }


        @JvmStatic
        fun newInstanceOfStrings(items: ArrayList<String>): SimpleChooserDialog {

            val args = Bundle()
            args.putBoolean(keyIsString, true)
            args.putStringArrayList(keyData, items)
            val fragment = SimpleChooserDialog()
            fragment.arguments = args
            return fragment
        }


        @JvmStatic
        fun newInstanceOfStrings(vararg items: String): SimpleChooserDialog {


            val list:ArrayList<String>  = ArrayList()
            items.flatMapTo(list) { arrayListOf(it)}

            val args = Bundle()
            args.putBoolean(keyIsString, true)
            args.putStringArrayList(keyData, list)
            val fragment = SimpleChooserDialog()
            fragment.arguments = args
            return fragment
        }


        @JvmStatic
        fun showStrings(fragmentManager: FragmentManager, vararg items: String, chooseListener: OnChooseListener):SimpleChooserDialog{
            val dialog = SimpleChooserDialog.newInstanceOfStrings(*items)
            dialog.setOnChooseListener(chooseListener)
            dialog.show(fragmentManager,simpleChooser)
            return dialog
        }

        @JvmStatic
        fun showStrings(fragmentManager: FragmentManager, items: ArrayList<String>, chooseListener: OnChooseListener):SimpleChooserDialog{
            val dialog = SimpleChooserDialog.newInstanceOfStrings(items)
            dialog.setOnChooseListener(chooseListener)
            dialog.show(fragmentManager,simpleChooser)
            return dialog
        }

        @JvmStatic
        fun showParcelables(fragmentManager: FragmentManager, vararg items: Parcelable, chooseListener: OnChooseListener):SimpleChooserDialog{
            val dialog = SimpleChooserDialog.newInstanceOfParcelables(*items)
            dialog.setOnChooseListener(chooseListener)
            dialog.show(fragmentManager,simpleChooser)
            return dialog
        }

        @JvmStatic
        fun showParcelables(fragmentManager: FragmentManager, items: ArrayList<Parcelable>, chooseListener: OnChooseListener):SimpleChooserDialog{
            val dialog = SimpleChooserDialog.newInstanceOfParcelables(items)
            dialog.setOnChooseListener(chooseListener)
            dialog.show(fragmentManager,simpleChooser)
            return dialog
        }

    }


    private var listener: OnChooseListener? = null

    private var binding: DialogSimpleChooserBinding? = null



    fun setOnChooseListener(navigator: OnChooseListener) {
        this.listener = navigator
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        binding = DialogSimpleChooserBinding.inflate(LayoutInflater.from(context))


        val data:ArrayList<Any> = ArrayList()
        if(arguments!!.getBoolean(keyIsString,true)){
            arguments!!.getStringArrayList(keyData)?.mapTo(data){
                it
            }
        }else{

            val parcelableData= arguments!!.getParcelableArrayList<Parcelable>(keyData)
            parcelableData.mapTo(data){
                it
            }
        }



        val adapter = ChooserDialogAdapter(listener,this)
        binding?.content?.adapter = adapter
        binding?.content?.layoutManager = LinearLayoutManager(context)
        binding?.content?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.data = data
        builder.setView(binding?.root)

        return builder.create()
    }

    private class ChooserDialogAdapter(var navigator: OnChooseListener?,val dialog:SimpleChooserDialog) : RecyclerViewAdapter<Any>() {
        override fun onBind(holder: BaseViewHolder, position: Int, item: Any) {
            val binding = holder.binding
            if (binding is ItemSimpleChooserBinding) {
                binding.content.text = item .toString()
                binding.content.setOnClickListener {
                    if (item != null) {
                        navigator?.onChoose(dialog,item)
                    }
                }
            }
        }

        override fun getItemLayout(position: Int, item: Any?): Int = R.layout.item_simple_chooser

    }
}