package com.jarvanmo.kal

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.jarvanmo.kal.util.MToast
import com.jarvanmo.kal.util.MToast.TYPE_INFO
import com.orhanobut.logger.Logger
import java.io.File
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.util.*


/**
 * extensions for {@link View}
 */

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.isGoneOrInvisible() = visibility == View.INVISIBLE || visibility == View.GONE

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


/***
 * extensions for Logger
 * **/
fun String.logI(args: String? = null) {
  Logger.d(this,args)
}

fun String.logD(args: String? = null) {
    Logger.d(this,args)
}

fun String.logW(args: String? = null) {
    Logger.w(this,args)
}

fun String.logE(args: String? = null) {
    Logger.e(this,args)
}


/***
 * extensions for MToast
 */
fun String.toast(@MToast.ToastType toastType: Int = TYPE_INFO) {
    MToast.show(this,toastType)
}

fun Int.toast(@MToast.ToastType toastType: Int = TYPE_INFO) {
    MToast.show(this,toastType)
}


/***
 * extension for bitmap
 */
/****
 * @param context if des not provided ,then context must be not null
 */
fun Bitmap.save(context:Context?=null,des: File? =null,compressFormat: Bitmap.CompressFormat=Bitmap.CompressFormat.PNG,quality:Int=100):File?{
    if (context == null&& des == null){
        throw IllegalArgumentException("context and des can't both be  NULL")
    }
    val file = if(des==null){
        des
    }else{

        val fileName = UUID.randomUUID().toString() + when (compressFormat) {
            Bitmap.CompressFormat.WEBP -> ".webp"
            Bitmap.CompressFormat.PNG -> ".png"
            else -> ".jpeg"
        }
        File(context!!.cacheDir,fileName)
    }
    val bos = BufferedOutputStream(FileOutputStream(file))
    this.compress(compressFormat, quality, bos)
    bos.flush()
    bos.close()

    return file
}





