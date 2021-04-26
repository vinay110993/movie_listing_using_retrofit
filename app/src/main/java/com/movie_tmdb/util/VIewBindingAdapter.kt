package com.movie_tmdb.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object VIewBindingAdapter{

    /**
     * binding adapter to set image over imageview
     * @param: url,
     * further call extension function to set image
     * @see com.movie_tmdb.util.setImage
     * */
    @JvmStatic
    @BindingAdapter("image_url")
    fun bindImage(view: ImageView, url: String?){
        if(url.isNullOrEmpty()) return
        view.context.setImage(view, "${AppConstants.IMAGE_PREFIX}$url")
    }

   /**
    * binding adapter to set view background dynamically
    * @param: backColor,
    * @param: cornerRadius,
    * requireAll: false, so there is no compulsion to send all values
    * */
    @JvmStatic
    @BindingAdapter( value = ["app:background_color", "app:corner_radius"], requireAll = false)
    fun setBackground(view: View, backColor: Int?, cornerRadius: Float?){
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius ?: 2f
        shape.setColor(backColor ?: Color.WHITE)

        //view.background = shape
    }

}