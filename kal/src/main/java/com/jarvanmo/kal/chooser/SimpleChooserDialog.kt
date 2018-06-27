package com.jarvanmo.kal.chooser

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
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
        private val extraType = "extraType"

        @JvmStatic
        fun newInstance(items: ArrayList<Parcelable>): SimpleChooserDialog {

            val args = Bundle()
            args.putParcelableArrayList(extraType, items)
            val fragment = SimpleChooserDialog()
            fragment.arguments = args
            return fragment
        }


    }


    private var listener: SimpleChooserNavigator? = null

    var binding: DialogSimpleChooserBinding? = null


    fun setItemClickListener(navigator: SimpleChooserNavigator) {
        this.listener = navigator
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        binding = DialogSimpleChooserBinding.inflate(LayoutInflater.from(context))

        val data: ArrayList<Parcelable> = arguments!!.getParcelableArrayList(extraType)
        val adapter = ChooserDialogAdapter(listener)
        binding?.content?.adapter = adapter
        binding?.content?.layoutManager = LinearLayoutManager(context)
        binding?.content?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.setData(data)
        builder.setView(binding?.root)

        return builder.create()
    }

    private class ChooserDialogAdapter(var navigator: SimpleChooserNavigator?) : RecyclerViewAdapter<Parcelable>() {
        override fun onBind(holder: BaseViewHolder?, position: Int, item: Parcelable?) {
            val binding = holder?.binding
            if (binding is ItemSimpleChooserBinding) {
                binding.content.text = item.toString()
                binding.content.setOnClickListener {
                    if (item != null) {
                        navigator?.onChoose(item)
                    }
                }
            }
        }

        override fun getItemLayout(position: Int, item: Parcelable?): Int = R.layout.item_simple_chooser

    }
}