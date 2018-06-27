package com.jarvanmo.kal

import android.view.View
import com.jarvanmo.kal.util.MToast
import com.jarvanmo.kal.util.MToast.TYPE_INFO
import com.orhanobut.logger.Logger

/**
 * extensions for {@link View}
 */

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.isGoneOrInvisible() = visibility == View.INVISIBLE || visibility == View.GONE

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
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






