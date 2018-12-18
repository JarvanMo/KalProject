package com.jarvanmo.kal.imageviewer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE
import android.view.ViewGroup
import android.widget.LinearLayout


import com.jarvanmo.kal.R
import com.jarvanmo.kal.databinding.ItemImageViewerBinding
import com.jarvanmo.kal.recyclerview.RecyclerViewAdapter
import okhttp3.OkHttpClient
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.annotation.GlideModule
import com.squareup.picasso.Picasso


class ImageViewerActivity : AppCompatActivity() {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recycler: RecyclerView
    private lateinit var  indicatorsWrapper:LinearLayout
    private val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val count =indicatorsWrapper.childCount
            val currentIndex = layoutManager.findFirstCompletelyVisibleItemPosition()
            (0..count).forEach {index->
               indicatorsWrapper.getChildAt(index)?.setBackgroundResource(
                       if(index == currentIndex){
                           R.drawable.shape_image_viewer_indicator_selected
                       }else{
                           R.drawable.shape_image_viewer_indicator_unselected
                       }
               )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        BigImageViewer.initialize(GlideImageLoader.with(applicationContext, OkHttpClient()))
        setContentView(R.layout.activity_image_viewer)
        val root = findViewById<View>(R.id.imageViewerRoot)
        indicatorsWrapper = findViewById(R.id.indicatorsWrapper)

        root.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_IMMERSIVE


        root.setOnClickListener { finish() }

        recycler = findViewById(R.id.imageViewer)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutManager = layoutManager
        val adapter = ImageViewerAdapter()
        val uris = intent.getParcelableArrayExtra(KEY_IMAGES).map { it as Uri }


        val currentIndex = intent.getIntExtra(KEY_CURRENT_INDEX, 0)
        indicatorsWrapper.removeAllViews()


        uris.takeIf { it.isNotEmpty() }?.forEachIndexed { index, _ ->
            val view = View(this@ImageViewerActivity)

            view.requestLayout()
            view.setBackgroundResource(if (currentIndex == index) R.drawable.shape_image_viewer_indicator_selected else R.drawable.shape_image_viewer_indicator_unselected)
            val indicatorSize = resources.getDimensionPixelSize(R.dimen.image_viewer_indicator_size)
            val layoutParams = LinearLayout.LayoutParams(indicatorSize,indicatorSize)
            layoutParams.setMargins(15,15,0,0)
            view.layoutParams = layoutParams
            indicatorsWrapper.addView(view)
        }


        adapter.data = uris
        recycler.adapter = adapter



        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)


        if (currentIndex != 0) {
            layoutManager.scrollToPositionWithOffset(currentIndex, 0)
        }

        recycler.addOnScrollListener(scrollListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        recycler.removeOnScrollListener(scrollListener)
    }


    private class ImageViewerAdapter : RecyclerViewAdapter<Uri>() {
        override fun getItemLayout(position: Int, item: Uri) = R.layout.item_image_viewer
        override fun onBind(holder: BaseViewHolder, position: Int, item: Uri) {
            val binding = holder.binding as ItemImageViewerBinding
//            binding.image.setProgressIndicator(ProgressPieIndicator())
            Picasso.get().load(item).into(binding.image)
//            binding.image.showImage(item)
            binding.root.setOnClickListener { v ->
                val context = v.context
                if (context is Activity) {
                    context.finish()
                }

            }
        }

    }


    companion object {
        const val KEY_IMAGES = "KEY_IMAGES"
        const val KEY_CURRENT_INDEX = "KEY_CURRENT_INDEX"


        @JvmStatic
        fun startImageViewer(context: Context, currentIndex: Int = 0, vararg images: Uri) {
            val intent = Intent(context, ImageViewerActivity::class.java)
            intent.putExtra(KEY_IMAGES, images)
            intent.putExtra(KEY_CURRENT_INDEX, currentIndex)
            context.startActivity(intent)
        }


        @JvmStatic
        fun startImageViewer(context: Context, images: List<Uri>, currentIndex: Int = 0) {

            val intent = Intent(context, ImageViewerActivity::class.java)
            intent.putExtra(KEY_IMAGES, images.toTypedArray())
            intent.putExtra(KEY_CURRENT_INDEX, currentIndex)
            context.startActivity(intent)
        }

        @JvmStatic
        fun startImageViewerWithStrings(context: Context, images: List<String>, currentIndex: Int = 0) {
            val results = images.map { it -> Uri.parse(it) }
            startImageViewer(context, results, currentIndex)
        }

    }

}
